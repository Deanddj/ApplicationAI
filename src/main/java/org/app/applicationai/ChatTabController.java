package org.app.applicationai;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class ChatTabController {

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextArea inputTextArea;

    private String language;

    public void setLanguage(String language) {
        this.language = language;
    }

    @FXML
    private void sendPrompt() {
        String userPrompt = inputTextArea.getText();
        chatTextArea.appendText("User: " + userPrompt + "\n");

        // Hier zou de logica komen voor het verzenden van de prompt naar de AI-assistent
        // en het ontvangen van een reactie, die dan wordt toegevoegd aan chatTextArea
        // Bijvoorbeeld:
        String aiResponse = "AI: This is a hardcoded response in " + language + ".";
        chatTextArea.appendText(aiResponse + "\n");

        inputTextArea.clear();
    }
}
