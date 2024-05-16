package org.app.applicationai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class TEST_startLoginScreen extends Application {
    private static ArrayList<User> users;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Operating System = " + System.getProperty("os.name"));

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        initialize();
        launch();
    }

    // Tijdelijke methoden om de login functie te testen
    public static void initialize() {
        users = new ArrayList<>();
        User user1 = new User("test@gmail.com", "test");
        User user2 = new User("test@outlook.com", "test");
        User user3 = new User("test@hhs.nl", "test");
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}