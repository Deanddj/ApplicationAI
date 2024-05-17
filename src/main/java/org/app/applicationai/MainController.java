package org.app.applicationai;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    private Button button_logout;

    @FXML
    private Button button_new_chat;
    @FXML
    private Button button_settings;

    @FXML
    private TabPane tabPane;

    @FXML
    private ComboBox<String> languageComboBox;

    private String selectedLanguage = "English";

    private int tabCount = 1;

    @FXML
    public void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList("English", "Dutch"));
        languageComboBox.setValue(selectedLanguage); // Set default value
        loadInitialChatTab();
    }

    @FXML
    private void loadInitialChatTab() {
        addNewTab(new ActionEvent());
    }

    @FXML
    private void addNewTab(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/app/applicationai/chat-tab.fxml"));
            AnchorPane chatTabContent = loader.load();

            ChatTabController chatTabController = loader.getController();
            chatTabController.setLanguage(selectedLanguage);

            Tab newTab = new Tab("Chat " + tabCount, chatTabContent);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab); // Selecteer de nieuwe tab automatisch

            tabCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateLanguage(ActionEvent event) {
        selectedLanguage = languageComboBox.getValue();
        for (Tab tab : tabPane.getTabs()) {
            ChatTabController chatTabController = (ChatTabController) tab.getContent().getUserData();
            if (chatTabController != null) {
                chatTabController.setLanguage(selectedLanguage);
            }
        }
    }
}
