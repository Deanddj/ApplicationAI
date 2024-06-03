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

    private final ArrayList<Tab> openTabs = new ArrayList<>();

    private static LanguageSubject languageSubject = LanguageManager.getInstance().getLanguageSubject();

    @FXML
    public void initialize() {
        // Laad de initiële chat-tab
        loadInitialChatTab();

        button_instellingen.setOnAction(event -> loadSettings());
        button_uitloggen.setOnAction(event -> loadLogin());

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
}
