package org.app.applicationai;

import org.app.applicationai.User;
import org.app.applicationai.loginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class LoginTest {

    private loginController loginController;
    private ArrayList<User> users;

    @BeforeEach
    public void setUp() {
        loginController = new loginController();

        users = new ArrayList<>();
        users.add(new User("user@gmail.com", "password123"));

    }

    @Test
    public void testOnjuisteGebruikersnaamEnWachtwoord() {
        boolean loggedIn = loginController.authenticateUser("verkeerd@gmail.com", "verkeerdWachtwoord", users);
        assertFalse(loggedIn);
    }

    @Test
    public void testJuisteGebruikersnaamOnjuistWachtwoord() {
        boolean loggedIn = loginController.authenticateUser("user@gmail.com", "verkeerdWachtwoord", users);
        assertFalse(loggedIn);
    }

    @Test
    public void testOnjuisteGebruikersnaamJuisteWachtwoord() {
        boolean loggedIn = loginController.authenticateUser("verkeerd@gmail.com", "password123", users);
        assertFalse(loggedIn);
    }

    @Test
    public void testJuisteGebruikersnaamEnWachtwoord() {
        boolean loggedIn = loginController.authenticateUser("user@gmail.com", "password123", users);
        assertTrue(loggedIn);
    }
}
