package org.app.applicationai;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Elasticsearch {
    public static void main(String[] args) {
        // !! TIJDELIJKE METHODE OM TE TESTEN !!

        // Pad naar de JSON file
        String jsonFilePath = "src/main/resources/org/app/applicationai/elasticsearch.json";

        // Keywords om te zoeken
        List<String> searchKeywords = List.of("vakantie", "ontslag", "opzeggen");

        // Methode aanroepen voor resultaat
        String result = searchDocumentation(jsonFilePath, searchKeywords);
        System.out.println(result);

        // !! TIJDELIJKE METHODE OM TE TESTEN !!
    }

    public static String searchDocumentation(String jsonFilePath, List<String> searchKeywords) {
        StringBuilder result = new StringBuilder();

        // JSON file laden
        try (InputStream is = new FileInputStream(jsonFilePath)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray allDocumentation = jsonObject.getJSONArray("allDocumentation");

            // Om documentatie op te slaan
            Set<String> matchedDocumentation = new HashSet<>();

            // Zoek en verzamel de documentatie gebaseerd op de keywords
            for (int i = 0; i < allDocumentation.length(); i++) {
                JSONObject doc = allDocumentation.getJSONObject(i);
                JSONArray keywords = doc.getJSONArray("keywords");

                // Check of de keywords matchen in de documentatie
                for (int j = 0; j < keywords.length(); j++) {
                    String keyword = keywords.getString(j);
                    for (String searchKeyword : searchKeywords) {
                        if (keyword.equalsIgnoreCase(searchKeyword)) {
                            matchedDocumentation.add(doc.getString("documentation"));
                            break;
                        }
                    }
                }
            }

            // Alle gematchde documentatie aan het resultaat toevoegen
            for (String documentation : matchedDocumentation) {
                result.append(documentation).append("\n\n");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Bestand niet gevonden: " + jsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString().trim();
    }
}