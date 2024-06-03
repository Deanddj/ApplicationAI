package org.app.applicationai;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SettingsController implements LanguageObserver {

    @FXML
    private Label language;

    @FXML
    private Label wachtwoord_Label;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private User user;

    @FXML
    private Button button_wijzigEmail;

    @FXML
    private Button button_wijzigPassword;

    @FXML
    private Button button_back;

    @FXML
    private ComboBox<String> languageComboBox;

    private static LanguageSubject languageSubject = LanguageManager.getInstance().getLanguageSubject();

    public void initialize() {
        user = new User("current@example.com", "currentPassword"); // Haal dit uit je gebruikerssessie
        emailField.setText(user.getEmail());
        button_back.setOnAction(event -> handleBack());
        languageComboBox.getItems().addAll("Nederlands", "Engels");
        languageSubject.addObserver(this);

        loadLanguage();
    }

    @FXML
    private void handleComboBoxAction(ActionEvent event) {
        String selectedLanguage = languageComboBox.getValue();
        languageSubject.setCurrentLanguage(selectedLanguage); // Wijzig en sla de nieuwe taal op
    }

    private void loadLanguage() {
        String selectedLanguage = languageSubject.getCurrentLanguage();
        applyLanguageChanges(selectedLanguage);
        System.out.println(selectedLanguage);
    }

    @Override
    public void applyLanguageChanges(String selectedLanguage) {
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
    public void handleSaveChanges() {
        String newEmail = emailField.getText();
        String newPassword = passwordField.getText();

        if (!newEmail.isEmpty()) {
            user.updateEmail(newEmail);
        }

        if (!newPassword.isEmpty()) {
            user.updatePassword(newPassword);
        }

        // Voeg extra logica toe om de wijzigingen op te slaan (bijv. update de database)
    }

    @FXML
    public void handleBack() {HelloApplication.switchScene("hello-view.fxml");
    }
}
