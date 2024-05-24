package org.app.applicationai;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.app.applicationai.ChatTabController;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button button_logout;

    @FXML
    private Button button_new_chat;
    @FXML
    private Button button_settings;

/*
    @FXML
    private ComboBox <String> languageComboBox;*/

    private String selectedLanguage = "English";


    @FXML
    private TabPane tabPane;

    private int tabCount = 1; // Begin met één chat-tab

    @FXML
    public void initialize() {
        // Laad de initiële chat-tab
        loadInitialChatTab();
    }

    @FXML
    private void loadInitialChatTab() {
        addNewTab(new ActionEvent());
    }

    @FXML
    private void addNewTab(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat-tab.fxml"));
            AnchorPane chatTabContent = loader.load();

            ChatTabController chatTabController = loader.getController();

            Tab newTab = new Tab("Chat " + tabCount, chatTabContent);
            tabPane.getTabs().add(newTab);

            tabCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*@FXML
    private void updateLanguage(ActionEvent event) {
        selectedLanguage = languageComboBox.getValue();
        for (Tab tab : tabPane.getTabs()) {
            ChatTabController chatTabController = (ChatTabController) tab.getContent().getUserData();
            if (chatTabController != null) {
                chatTabController.setLanguage(selectedLanguage);
            }
        }
    }*/
}
