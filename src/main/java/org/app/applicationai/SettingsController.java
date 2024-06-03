package org.app.applicationai;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsController implements LanguageObserver {

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

    private User user;
    String user_file = "src/main/resources/org/app/applicationai/changeuser.json";

    private static LanguageSubject languageSubject = LanguageManager.getInstance().getLanguageSubject();

    public void initialize() {
        loadUserData();
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

    private String loadLanguage() {
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
            saveUserData();
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
            saveUserData();
            label_wijzigPassword.setText("Wachtwoord veranderd");
        } else {
            label_wijzigPassword.setText("Ongeldig wachtwoord");
        }
    }

    @FXML
    public void handleBack() {
        HelloApplication.goToChatScene();
    }

    private boolean checkEmail(String email) {
        return email.contains("@");
    }

    private boolean checkPassword(String password) {
        return password.length() >= 6;
    }

    private void loadUserData() {
        File file = new File(user_file);
        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(user_file)));
                JSONObject json = new JSONObject(content);
                String email = json.getString("email");
                String password = json.getString("password");
                user = new User(email, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            user = new User("test@gmail.com", "wachtwoord");
        }
    }

    private void saveUserData() {
        File file = new File(user_file);
        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(user_file)));
                JSONObject json = new JSONObject(content);
                json.put("email", user.getEmail());
                json.put("password", user.getPassword());

                FileWriter fileWriter = new FileWriter(user_file);
                fileWriter.write(json.toString(4));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
