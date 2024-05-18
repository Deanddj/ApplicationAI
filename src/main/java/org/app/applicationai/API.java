package org.app.applicationai;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class API {
    public static void main(String[] args) {
        // !! TIJDELIJKE METHODE OM TE TESTEN !!

        // API-object aanmaken
        API api = new API("158.178.151.211", "llama3:8b");

        // Een vraag stellen aan de AI
        api.connect("Hallo, kun je zeggen wie je bent?");

        // !! TIJDELIJKE METHODE OM TE TESTEN !!
    }

    private final String host;
    private int port = 11434;
    private final String model;

    public API(String host, int port, String model) {
        this.host = host;
        this.port = port;
        this.model = model;
    }

    public API(String host, String model) {
        this.host = host;
        this.model = model;
    }

    public void connect(String question) {
        try {
            // API endpoint URL
            String apiUrl = "http://" + host + ":" + port + "/api/generate";

            // JSON payload
            String payload = "{\"model\": \"" + model + "\", \"prompt\": \"" + question + "\"}";

            // URL-object maken
            URL url = new URL(apiUrl);

            // HttpURLConnection maken
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Request methode naar POST zetten
            connection.setRequestMethod("POST");

            // Headers configureren
            connection.setRequestProperty("Content-Type", "application/json");

            // Aanzetten van input/output
            connection.setDoOutput(true);

            // payload schrijven voor body
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(payload);
                outputStream.flush();
            }

            // Response code printen (200=Succesvol)
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Response body lezen en printen
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    JSONObject jsonResponse = new JSONObject(inputLine);
                    if (jsonResponse.has("response")) {
                        System.out.print(jsonResponse.getString("response"));
                    }
                }
            }

            // Connectie sluiten
            connection.disconnect();
        } catch (IOException e) {
            // Exception behandelen
            System.err.println("Er is een fout opgetreden bij het verbinden met de API: " + e.getMessage());
        }
    }
}