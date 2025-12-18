package org.app.applicationai;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class loginController extends Controller {
    @FXML
    private AnchorPane topPane;
    @FXML
    private Button cancelButton, minimizeButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    private double xOffset = 0;
    private double yOffset = 0;

    public void loginButtonOnAction(ActionEvent e) {
        System.out.println("Login button clicked!");
        System.out.println("Username: " + usernameTextField.getText());
        System.out.println("Password: " + passwordPasswordField.getText());
        
        if (usernameTextField.getText().isEmpty() || passwordPasswordField.getText().isEmpty()) {
            System.out.println("Vul a.u.b. alle velden in");
            return;
        }

        ArrayList<User> users = HelloApplication.getUsers();
        System.out.println("Number of users: " + (users != null ? users.size() : "null"));
        
        if (users == null || users.isEmpty()) {
            System.out.println("No users found!");
            return;
        }
        
        boolean loginSuccess = false;
        for (User user : users) {
            System.out.println("Checking user: " + user.getEmail());
            if (usernameTextField.getText().equals(user.getEmail())) {
                System.out.println("Email matches!");
                if (passwordPasswordField.getText().equals(user.getPassword())) {
                    System.out.println("U heeft toegang (Email: " + user.getEmail() +" - Wachtwoord: " + user.getPassword() + ")");
                    Stage currentStage = (Stage) cancelButton.getScene().getWindow();
                    SceneManager.switchScene("hello-view.fxml", currentStage);
                    loginSuccess = true;
                    break;
                } else {
                    System.out.println("Password does not match!");
                }
            }
        }
        
        if (!loginSuccess) {
            System.out.println("Login failed - invalid credentials");
        }
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

    @Override
    public void initialize() {

    }
}
