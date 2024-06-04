package org.app.applicationai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        SceneManager.switchScene("hello-view.fxml", stage);
        stage.setTitle("ChatAI");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}