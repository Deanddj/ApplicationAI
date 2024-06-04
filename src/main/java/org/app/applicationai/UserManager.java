package org.app.applicationai;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserManager {
    private User user;
    String user_file = "src/main/resources/org/app/applicationai/changeuser.json";
    public User loadUserData() {
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
        return user;
    }

    public void saveUserData() {
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
