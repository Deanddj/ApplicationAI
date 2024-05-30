package org.app.applicationai;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.*;

public class ChatTabController {

    @FXML
    private Button button_send;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private Label keywordLabel;

    private String userMessage;

    private String documentatie;

    private static final List<String> PREDEFINED_KEYWORDS = Elasticsearch.getKeywords("src/main/resources/org/app/applicationai/elasticsearch.json");

    private String language;

    public void setLanguage(String language) {
        this.language = language;
    }


    @FXML
    private void sendPrompt() {
        String userPrompt = inputTextArea.getText();

        chatTextArea.appendText("User: " + userPrompt + "\n\n");
        Set<String> overlappingKeywords = filterKeywords(userPrompt);
        List<String> keywords = new ArrayList<>(overlappingKeywords);
        // Hier zou de logica komen voor het verzenden van de prompt naar de AI-assistent
        // en het ontvangen van een reactie, die dan wordt toegevoegd aan chatTextArea
        // Bijvoorbeeld:


        new Thread(() -> {
            documentatie = Elasticsearch.startElasticSearch(keywords);
            chatTextArea.appendText("AI: ");
            API.starAI(userPrompt, documentatie, chatTextArea);
            chatTextArea.appendText("\n\n");
        }).start();


        /*StringBuilder aiResponse = new StringBuilder("AI: " + documentatie);
        // Enhanced for loop
        for (String name : overlappingKeywords) {
            aiResponse.append(name).append(", ");
        }
        */

        //chatTextArea.appendText(aiResponse + "\n");

        inputTextArea.clear();
    }
    public void getMessage() {
        userMessage = messageTextArea.getText();
        Set<String> overlappingKeywords = filterKeywords(userMessage);
        keywordLabel.setText("Overlapping Keywords: " + overlappingKeywords);
        clearMessage();

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
}

