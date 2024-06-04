package org.app.applicationai;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;
    private static Scene chatScene;
    public static void switchScene(String fxml, Stage stage) {
        primaryStage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);

            // Als we teruggaan naar het chat scherm, laad dan de geopende tabs
            if (fxml.equals("hello-view.fxml")) {
                HelloController controller = fxmlLoader.getController();
                controller.loadOpenTabs();
            }

            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Sla de chat-scene op
            if (fxml.equals("hello-view.fxml")) {
                chatScene = scene;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void goToChatScene() {
        primaryStage.setScene(chatScene);
    }
}
