package org.app.applicationai;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

public class ChatTabController implements LanguageObserver{

    @FXML
    private Button button_send;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextArea inputTextArea;

    private String userMessage;

    private String documentatie;

    private static final List<String> PREDEFINED_KEYWORDS = Elasticsearch.getKeywords("src/main/resources/org/app/applicationai/elasticsearch.json");

    private HelloController helloController;

    private int tabCount;

    private boolean tabNameChanged = false;

    private static LanguageSubject languageSubject = LanguageManager.getInstance().getLanguageSubject();

    public void ChatTabController (int tabCount){
        this.tabCount = tabCount;
    }
    public void setTabCount(int tabCount) {
        this.tabCount = tabCount;
    }
    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }
    @FXML
    public void initialize() {
        languageSubject.addObserver(this);
        String selectedLanguage = loadLanguage();
        applyLanguageChanges(selectedLanguage);
    }
    @FXML
    private void sendPrompt() {
        String userPrompt = inputTextArea.getText();
        chatTextArea.appendText("User: " + userPrompt + "\n\n");
        Set<String> overlappingKeywords = filterKeywords(userPrompt);
        List<String> keywords = new ArrayList<>(overlappingKeywords);

        if (!keywords.isEmpty() && !tabNameChanged) {
            helloController.changeTabName(tabCount - 1, keywords.get(0));
            tabNameChanged = true;
        }
        new Thread(() -> {
            documentatie = Elasticsearch.startElasticSearch(keywords);
            chatTextArea.appendText("AI: ");
            API.starAI(userPrompt, documentatie, chatTextArea);
            chatTextArea.appendText("\n\n");
        }).start();

        inputTextArea.clear();
    }

    public Set<String> filterKeywords(String text) {
        // Convert the input text to lowercase for case-insensitive matching
        text = text.toLowerCase();

        // Filter keywords that overlap with the predefined list
        Set<String> overlappingKeywords = new HashSet<>();
        for (String keyword : PREDEFINED_KEYWORDS) {
            // Check if the keyword is present in the input text
            if (text.contains(keyword)) {
                overlappingKeywords.add(keyword);
            }
        }
        return overlappingKeywords;
    }

    public void clearMessage() {
        messageTextArea.clear();
    }

    @Override
    public void applyLanguageChanges(String selectedLanguage) {
        if ("Dutch".equals(selectedLanguage) || "Nederlands".equals(selectedLanguage)) {
            button_send.setText("Verstuur");
            Font font = Font.font("Arial", FontWeight.BOLD, 14.8);
            button_send.setFont(font);
        } else {
            button_send.setText("Send");
            Font font = Font.font("Arial", FontWeight.BOLD, 18);
            button_send.setFont(font);
        }
    }

    public String loadLanguage() {
        String selectedLanguage = languageSubject.getCurrentLanguage();
        return selectedLanguage;
    }
}



