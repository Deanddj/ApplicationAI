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
import java.util.ArrayList;

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

    private int tabCount = 1;
    private final ArrayList<Tab> openTabs = new ArrayList<>();

    @FXML
    public void initialize() {
        loadInitialChatTab();
        button_settings.setOnAction(event -> loadSettings());
        button_logout.setOnAction(event -> loadLogin());
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
            openTabs.add(newTab);
            tabCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void loadSettings() {
        saveOpenTabs();
        HelloApplication.switchScene("Settings.fxml");
    }
    @FXML
    public void loadLogin() {
        saveOpenTabs();
        HelloApplication.switchScene("login-screen.fxml");
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
    private void saveOpenTabs() {
        openTabs.clear();
        openTabs.addAll(tabPane.getTabs());
    }

    private void restoreOpenTabs() {
        tabPane.getTabs().clear();
        tabPane.getTabs().addAll(openTabs);
    }

    public void loadOpenTabs() {
        restoreOpenTabs();
    }
}

