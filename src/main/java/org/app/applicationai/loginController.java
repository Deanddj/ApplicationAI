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

public class loginController {
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
        if (usernameTextField.getText().isEmpty() || passwordPasswordField.getText().isEmpty()) {
            System.out.println("Vul a.u.b. alle velden in");
            return;
        }

        ArrayList<User> users = TEST_startLoginScreen.getUsers();
        for (User user : users) {
            if (usernameTextField.getText().equals(user.getEmail())) {
                if (passwordPasswordField.getText().equals(user.getPassword())) {
                    System.out.println("U heeft toegang (Email: " + user.getEmail() +" - Wachtwoord: " + user.getPassword() + ")");
                    Stage currentStage = (Stage) cancelButton.getScene().getWindow();
                    SceneManager.switchScene("hello-view.fxml", currentStage);
                }
            }
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
}
