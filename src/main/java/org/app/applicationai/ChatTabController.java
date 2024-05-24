package org.app.applicationai;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatTabController {
//
    @FXML
    private Button button_send;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Label keywordLabel;

    private String userMessage;

    private Set<String> overlappingKeywords;
    private static final List<String> PREDEFINED_KEYWORDS = Arrays.asList(
            "domain-model", "financial-system", "social-platform-application", "functional-requirements", "vakantie", "ontslag", "opzeggen");

    public void getMessage() {
        userMessage = messageTextArea.getText();
        overlappingKeywords = filterKeywords(userMessage);
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

