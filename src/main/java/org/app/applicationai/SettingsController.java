package org.app.applicationai;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private User user;

    @FXML
    private Button button_wijzigEmail;

    @FXML
    private Button button_wijzigPassword;



    public void initialize() {
        user = new User("current@example.com", "currentPassword"); // Haal dit uit je gebruikerssessie
        emailField.setText(user.getEmail());
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
    public void handleBack() {
        HelloApplication.switchScene("hello-view.fxml");
    }
}
