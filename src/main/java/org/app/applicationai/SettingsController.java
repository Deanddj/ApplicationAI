package org.app.applicationai;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsController {

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

    private User user;

    String user_file = "src/main/resources/org/app/applicationai/changeuser.json";

    public void initialize() {
        loadUserData();
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        button_back.setOnAction(event -> handleBack());
        button_wijzigEmail.setOnAction(event -> handleChangeEmail());
        button_wijzigPassword.setOnAction(event -> handleChangePassword());
    }

    @FXML
    public void handleChangeEmail() {
        String newEmail = emailField.getText();
        if (!newEmail.isEmpty() && checkEmail(newEmail)) {
            user.updateEmail(newEmail);
            saveUserData();
            label_wijzigEmail.setText("E-mail veranderd naar: " + newEmail);
        } else {
            label_wijzigEmail.setText("Ongeldig e-mail");
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