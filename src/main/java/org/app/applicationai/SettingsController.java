package org.app.applicationai;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class SettingsController extends Controller implements LanguageObserver {

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

    private static LanguageSubject languageSubject = LanguageManager.getInstance().getLanguageSubject();
    String selectedLanguage = loadLanguage();

    public void initialize() {
        userManager = new UserManager();
        user = userManager.loadUserData();

        languageSubject.addObserver(this);

        applyLanguageChanges();
        loadGui();
    }

    @Override
    public void loadGui() {
        button_wijzigEmail.setOnAction(event -> handleChangeEmail());
        button_wijzigPassword.setOnAction(event -> handleChangePassword());
        button_back.setOnAction(event -> handleBack());

        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        languageComboBox.getItems().addAll("Nederlands", "English");
    }

    @FXML
    private void handleComboBoxAction(ActionEvent event) {
        selectedLanguage = languageComboBox.getValue();
        languageSubject.setCurrentLanguage(selectedLanguage);
        label_wijzigEmail.setText(null);
        label_wijzigPassword.setText(null);
    }

    @Override
    public String loadLanguage() {
        selectedLanguage = languageSubject.getCurrentLanguage();
        return selectedLanguage;
    }

    @Override
    public void update(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
        applyLanguageChanges();
    }


    public void applyLanguageChanges() {
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
        user = userManager.loadUserData();
        String currentEmail = user.getEmail();
        label_wijzigEmail.setTextFill(Color.RED);

        if (!newEmail.isEmpty() && checkEmail(newEmail)) {
            if (newEmail.equals(currentEmail)) {
                if (selectedLanguage.equals("Nederlands")) {
                    label_wijzigEmail.setText("Nieuwe email kan niet zelfde zijn als de oude");
                }
                else {
                    label_wijzigEmail.setText("New email can't be the same as old");
                }
                return;
            }

            user.updateEmail(newEmail);
            userManager.saveUserData();
            label_wijzigEmail.setText("✓");
            label_wijzigEmail.setTextFill(Color.GREEN);
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
        user = userManager.loadUserData();
        String currentPassport = user.getPassword();
        label_wijzigPassword.setTextFill(Color.RED);


        if (!newPassword.isEmpty() && checkPassword(newPassword)) {
            if (newPassword.equals(currentPassport)) {
                if (selectedLanguage.equals("Nederlands")) {
                    label_wijzigPassword.setText("Nieuwe wachtwoord kan niet zelfde zijn als de oude");
                }
                else {
                    label_wijzigPassword.setText("New password can't be the same as old");
                }
                return;
            }
            user.updatePassword(newPassword);
            userManager.saveUserData();
            label_wijzigPassword.setText("✓");
            label_wijzigPassword.setTextFill(Color.GREEN);
        } else {
            if (selectedLanguage.equals("Nederlands")) {
                label_wijzigPassword.setText("Ongeldig wachtwoord");
            } else {
                label_wijzigPassword.setText("Invalid password");
            }
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
