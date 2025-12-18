package org.app.applicationai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    private static ArrayList<User> users;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("ChatAI");
        stage.show();
    }

    public static void main(String[] args) {
        initialize();
        launch();
    }

    public static void initialize() {
        UserManager manager = new UserManager();
        User user = manager.loadUserData();
        users = new ArrayList<>();
        users.add(user);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}