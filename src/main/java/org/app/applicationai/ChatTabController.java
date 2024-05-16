package org.app.applicationai;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ChatTabController {

    @FXML
    private Button button_send;

    @FXML
    private TextArea messageTextArea;

    public String getMessage() {
        return messageTextArea.getText();
    }

    public void clearMessage() {
        messageTextArea.clear();
    }
}

