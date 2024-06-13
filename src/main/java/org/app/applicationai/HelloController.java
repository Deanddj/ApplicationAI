package org.app.applicationai;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloController extends Controller implements LanguageObserver {

    @FXML
    private Button cancelButton, minimizeButton;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button button_instellingen;

    @FXML
    private Button button_uitloggen;

    @FXML
    private Button button_new_chat;

    @FXML
    private AnchorPane topPane;

    @FXML
    private TabPane tabPane;

    private int tabCount = 1;

    private final ArrayList<Tab> openTabs = new ArrayList<>();

    private static LanguageSubject languageSubject = LanguageManager.getInstance().getLanguageSubject();

    private Stage currentStage;
    String selectedLanguage = loadLanguage();

    @FXML
    @Override
    public void initialize() {
        loadInitialChatTab();

        languageSubject.addObserver(this);
        applyLanguageChanges();
        loadGui();
    }

    @FXML
    @Override
    public void loadGui() {
        button_instellingen.setOnAction(event -> loadSettings());
        button_uitloggen.setOnAction(event -> loadLogin());
        cancelButton.setOnAction(event -> cancelButtonOnAction());
        minimizeButton.setOnAction(event -> minimizeButtonOnAction());
        button_new_chat.setOnAction(event -> addNewTab());
    }

    @FXML
    private void loadInitialChatTab() {
        addNewTab();
    }

    @FXML
    private void addNewTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat-tab.fxml"));
            AnchorPane chatTabContent = loader.load();
            ChatTabController chatTabController = loader.getController();

            chatTabController.setTabCount(tabCount);
            chatTabController.setHelloController(this);

            Tab newTab = new Tab("Chat " + tabCount, chatTabContent);
            tabPane.getTabs().add(newTab);
            openTabs.add(newTab);
            tabCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTabName(int tabIndex, String newName) {
        if (tabIndex >= 0 && tabIndex < openTabs.size()) {
            Tab tab = openTabs.get(tabIndex);
            tab.setText(newName);
        } else {
            System.out.println("Tab index out of bounds.");
        }
    }

    @FXML
    public void loadSettings() {
        saveOpenTabs();
        currentStage = (Stage) cancelButton.getScene().getWindow();
        SceneManager.switchScene("Settings.fxml", currentStage);
    }
    @FXML
    public void loadLogin() {
        saveOpenTabs();
        currentStage = (Stage) cancelButton.getScene().getWindow();
        SceneManager.switchScene("login-screen.fxml", currentStage);
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
    public void update(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
        applyLanguageChanges();
    }


    public void applyLanguageChanges() {
        if ("Dutch".equals(selectedLanguage) || "Nederlands".equals(selectedLanguage)) {
            button_instellingen.setText("      Instellingen");
            button_uitloggen.setText("    Uitloggen");
            button_new_chat.setText("Nieuwe Chat");
        } else {
            button_instellingen.setText("      Settings");
            button_uitloggen.setText("    Logout");
            button_new_chat.setText("New Chat");
        }
    }

    @Override
    public String loadLanguage() {
        String selectedLanguage = languageSubject.getCurrentLanguage();
        return selectedLanguage;
    }

    public void cancelButtonOnAction () {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void minimizeButtonOnAction () {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    /* Een sleep functie van de GUI om het scherm te verplaatsen */
    public void topPaneOnDragged(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) topPane.getScene().getWindow();
        stage.setY(mouseEvent.getScreenY() - yOffset);
        stage.setX(mouseEvent.getScreenX() - xOffset);
    }

    public void topPaneOnPressed(javafx.scene.input.MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }
}
