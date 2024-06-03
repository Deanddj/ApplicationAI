package org.app.applicationai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    private static Scene chatScene;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        switchScene("hello-view.fxml");
        stage.setTitle("ChatAI");
        primaryStage.setResizable(false);
        stage.show();
    }
    public static void switchScene(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);

            // Als we teruggaan naar het chat scherm, laad dan de geopende tabs
            if (fxml.equals("hello-view.fxml")) {
                HelloController controller = fxmlLoader.getController();
                controller.loadOpenTabs();
            }

            primaryStage.setScene(scene);

            // Sla de chat-scene op
            if (fxml.equals("hello-view.fxml")) {
                chatScene = scene;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Methode om terug te keren naar het chat-scherm
    public static void goToChatScene() {
        primaryStage.setScene(chatScene);
    }
    public static void main(String[] args) {
        launch();
    }
}