package org.app.applicationai;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsController implements LanguageObserver {

    @FXML
    private Button cancelButton, minimizeButton;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Label language;

    @FXML
    private Label wachtwoord_Label;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label label_wijzigEmail;

    @FXML
    private Label label_wijzigPassword;

    @FXML
    private Button button_wijzigEmail;

    @FXML
    private Button button_wijzigPassword;

    @FXML
    private Button button_back;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private AnchorPane topPane;

    private User user;
    private UserManager userManager;
    String user_file = "src/main/resources/org/app/applicationai/changeuser.json";

    private static LanguageSubject languageSubject = LanguageManager.getInstance().getLanguageSubject();

    public void initialize() {
        userManager = new UserManager();
        user = userManager.loadUserData();
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        button_wijzigEmail.setOnAction(event -> handleChangeEmail());
        button_wijzigPassword.setOnAction(event -> handleChangePassword());

        button_back.setOnAction(event -> handleBack());

        languageComboBox.getItems().addAll("Nederlands", "Engels");
        languageSubject.addObserver(this);

        String selectedLanguage = loadLanguage();
        applyLanguageChanges(selectedLanguage);
    }

    @FXML
    private void handleComboBoxAction(ActionEvent event) {
        String selectedLanguage = languageComboBox.getValue();
        languageSubject.setCurrentLanguage(selectedLanguage); // Wijzig en sla de nieuwe taal op
    }

    @Override
    public String loadLanguage() {
        String selectedLanguage = languageSubject.getCurrentLanguage();
        return selectedLanguage;
    }

    @Override
    public void applyLanguageChanges(String selectedLanguage) {
        languageComboBox.setValue(selectedLanguage);
        if ("Dutch".equals(selectedLanguage) || "Nederlands".equals(selectedLanguage)) {
            language.setText("Kies je taal");
            wachtwoord_Label.setText("Wachtwoord");
            button_wijzigEmail.setText("Wijzig");
            button_wijzigPassword.setText("Wijzig");
        } else {
            language.setText("Choose your language");
            wachtwoord_Label.setText("Password");
            button_wijzigEmail.setText("Change");
            button_wijzigPassword.setText("Change");
        }
    }

    @FXML
    public void handleChangeEmail() {
        String newEmail = emailField.getText();
        String selectedLanguage = loadLanguage();

        if (!newEmail.isEmpty() && checkEmail(newEmail)) {
            user.updateEmail(newEmail);
            userManager.saveUserData();
            if (selectedLanguage.equals("Nederlands")) {
                label_wijzigEmail.setText("Veranderd: " + newEmail);
            }
            else {
                label_wijzigEmail.setText("Changed: " + newEmail);
            }
        } else {
            if (selectedLanguage.equals("Nederlands")) {
                label_wijzigEmail.setText("Ongeldig e-mail: " + newEmail);
            }
            else {
                label_wijzigEmail.setText("Invalid e-mail");
            }
        }
    }

    @FXML
    public void handleChangePassword() {
        String newPassword = passwordField.getText();
        if (!newPassword.isEmpty() && checkPassword(newPassword)) {
            user.updatePassword(newPassword);
            userManager.saveUserData();
            label_wijzigPassword.setText("Wachtwoord veranderd");
        } else {
            label_wijzigPassword.setText("Ongeldig wachtwoord");
        }
    }

    @FXML
    public void handleBack() {

        SceneManager.goToChatScene();
    }

    private boolean checkEmail(String email) {
        return email.contains("@");
    }

    private boolean checkPassword(String password) {
        return password.length() >= 6;
    }

    public void cancelButtonOnAction (ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void minimizeButtonOnAction (ActionEvent e) {
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
