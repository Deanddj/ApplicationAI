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

    private String documentatie1;
    private String documentatie2;

    private static final List<String> PREDEFINED_KEYWORDS_ELASTICSEARCH = Elasticsearch.getKeywords("src/main/resources/org/app/applicationai/elasticsearch.json");
    private static final List<String> PREDEFINED_KEYWORDS_RESOURCESELECTOR = ResourceSelector.getKeywords("src/main/resources/org/app/applicationai/resourceSelector.json");
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
        Set<String> overlappingKeywords = filterKeywords(userPrompt,PREDEFINED_KEYWORDS_ELASTICSEARCH);
        Set<String> overlappingKeywords2 = filterKeywords(userPrompt,PREDEFINED_KEYWORDS_RESOURCESELECTOR);
        System.out.println("Overlappende keywords1: " + overlappingKeywords);
        System.out.println("Overlappende keywords2: " + overlappingKeywords2);
        //overlappingKeywords.addAll(overlappingKeywords2);
        List<String> keywords = new ArrayList<>(overlappingKeywords);
        List<String> keywords2 = new ArrayList<>(overlappingKeywords2);

        List<Set<String>> listOfSets = new ArrayList<>();
        if (!overlappingKeywords.isEmpty()) {
            listOfSets.add(overlappingKeywords);
        }

        if (!overlappingKeywords2.isEmpty()) {
            listOfSets.add(overlappingKeywords2);
        }

        System.out.println("Totale Set lijst: "+ listOfSets);

        System.out.println("keywords gepakt uit file1: " + keywords);
        System.out.println("keywords gepakt uit file2: " + keywords2);
        if (!keywords.isEmpty() || !keywords2.isEmpty() && !tabNameChanged) {
            if (!keywords.isEmpty()) {
                helloController.changeTabName(tabCount - 1, keywords.get(0));
                System.out.println("keywords 1 is gevuld");
            }
            else{
                helloController.changeTabName(tabCount - 1, keywords2.get(0));
                System.out.println("keywords 2 is gevuld");
            }
            tabNameChanged = true;
        }

        new Thread(() -> {
            documentatie1 = Elasticsearch.startElasticSearch(keywords);
            documentatie2 = ResourceSelector.startResourceSelector(keywords2);
            String documentaties =documentatie1.concat(documentatie2);
            System.out.println(documentaties);
            chatTextArea.appendText("AI: ");
            API.starAI(userPrompt, documentaties, chatTextArea);
            chatTextArea.appendText("\n\n");
        }).start();

        inputTextArea.clear();
    }

    public Set<String> filterKeywords(String text, List<String> keywords) {
        // Convert the input text to lowercase for case-insensitive matching
        text = text.toLowerCase();

        // Filter keywords that overlap with the predefined list
        Set<String> overlappingKeywords = new HashSet<>();
      //  PREDEFINED_KEYWORDS_ELASTICSEARCH.addAll(PREDEFINED_KEYWORDS_RESOURCESELECTOR);

        for (String keyword : keywords) {
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



