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

public class HelloController implements LanguageObserver {

    @FXML
    private Button button_instellingen;

    @FXML
    private Button button_uitloggen;

    @FXML
    private Button button_new_chat;

    @FXML
    private TabPane tabPane;

    private int tabCount = 1; // Begin met één chat-tab

    private static LanguageSubject languageSubject = LanguageManager.getInstance().getLanguageSubject();

    @FXML
    public void initialize() {
        // Laad de initiële chat-tab
        loadInitialChatTab();
        button_instellingen.setOnAction(event -> loadSettings());
        System.out.println(languageSubject);
        languageSubject.addObserver(this);
        loadLanguage();
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


    @FXML
    public void loadSettings() {
        HelloApplication.switchScene("Settings.fxml");
    }

    @Override
    public void applyLanguageChanges(String selectedLanguage) {
        if ("Dutch".equals(selectedLanguage) || "Nederlands".equals(selectedLanguage)) {
            button_instellingen.setText("      Instellingen");
            button_uitloggen.setText("    Uitloggen");
        } else {
            button_instellingen.setText("      Settings");
            button_uitloggen.setText("    Logout");
        }
    }

    private void loadLanguage() {
        String selectedLanguage = languageSubject.getCurrentLanguage();
        applyLanguageChanges(selectedLanguage);
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
