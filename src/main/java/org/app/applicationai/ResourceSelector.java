package org.app.applicationai;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ResourceSelector {
    public static void main(String[] args) {
        // !! TIJDELIJKE METHODE OM TE TESTEN !!

        // Pad naar de JSON file
        String jsonFilePath = "src/main/resources/org/app/applicationai/resourcetest.json";

        // Keywords om te zoeken
        List<String> searchKeywords = List.of("programming-language");

        // Methode aanroepen voor resultaat
        String result = searchDocumentation(jsonFilePath, searchKeywords);
        System.out.println(result);

        // !! TIJDELIJKE METHODE OM TE TESTEN !!
    }

    public static String startResourceSelector(List<String> keywords){
        // Pad naar de JSON file
        String jsonFilePath = "src/main/resources/org/app/applicationai/resourcetest.json";

        // Keywords om te zoeken
        List<String> searchKeywords = keywords;

        // Methode aanroepen voor resultaat
        String result = searchDocumentation(jsonFilePath, searchKeywords);
        System.out.println(result);
        return result;

    }

    public static String searchDocumentation(String jsonFilePath, List<String> searchKeywords) {
        StringBuilder result = new StringBuilder();

        // JSON file laden
        try (InputStream is = new FileInputStream(jsonFilePath)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray allDocumentation = jsonObject.getJSONArray("foundDocumentation");

            // Om documentatie op te slaan
            Set<JSONObject> matchedDocumentation = new HashSet<>();

            // Zoek en verzamel de documentatie gebaseerd op de keywords
            for (int i = 0; i < allDocumentation.length(); i++) {
                JSONObject doc = allDocumentation.getJSONObject(i);
                JSONArray keywords = doc.getJSONArray("keywords");

                // Check of de keywords matchen in de documentatie
                for (int j = 0; j < keywords.length(); j++) {
                    String keyword = keywords.getString(j);
                    for (String searchKeyword : searchKeywords) {
                        if (keyword.equalsIgnoreCase(searchKeyword)) {
                            matchedDocumentation.add(doc.getJSONObject("documentation"));
                            break;
                        }
                    }
                }
            }

            // Alle gematchde documentatie aan het resultaat toevoegen
            for (JSONObject documentation : matchedDocumentation) {
                result.append(formatDocumentation(documentation)).append("\n\n");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Bestand niet gevonden: " + jsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString().trim();
    }

    private static String formatDocumentation(JSONObject documentation) {
        StringBuilder formattedDoc = new StringBuilder();

        // Voeg taalbeschrijving toe
        if (documentation.has("languageDescription")) {
            formattedDoc.append("Language Description: ").append(documentation.getString("languageDescription")).append("\n");
        }

        // Voeg features toe
        if (documentation.has("features")) {
            formattedDoc.append("Features:\n");
            JSONArray features = documentation.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                formattedDoc.append(" - ").append(feature.getString("name")).append(": ").append(feature.getString("description")).append("\n");
            }
        }

        // Voeg libraries toe
        if (documentation.has("libraries")) {
            formattedDoc.append("Libraries:\n");
            JSONArray libraries = documentation.getJSONArray("libraries");
            for (int i = 0; i < libraries.length(); i++) {
                JSONObject library = libraries.getJSONObject(i);
                formattedDoc.append(" - ").append(library.getString("name")).append(": ").append(library.getString("description")).append("\n");
            }
        }

        // Voeg tools toe
        if (documentation.has("tools")) {
            formattedDoc.append("Tools:\n");
            JSONArray tools = documentation.getJSONArray("tools");
            for (int i = 0; i < tools.length(); i++) {
                JSONObject tool = tools.getJSONObject(i);
                formattedDoc.append(" - ").append(tool.getString("name")).append(": ").append(tool.getString("description")).append("\n");
            }
        }

        return formattedDoc.toString();
    }

    public static List<String> getKeywords(String jsonFilePath) {
        Set<String> keywordsSet = new HashSet<>();

        // JSON file laden
        try (InputStream is = new FileInputStream(jsonFilePath)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray allDocumentation = jsonObject.getJSONArray("foundDocumentation");

            // Verzamel alle unieke keywords
            for (int i = 0; i < allDocumentation.length(); i++) {
                JSONObject doc = allDocumentation.getJSONObject(i);
                JSONArray keywords = doc.getJSONArray("keywords");

                for (int j = 0; j < keywords.length(); j++) {
                    keywordsSet.add(keywords.getString(j));
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Bestand niet gevonden: " + jsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert Set to List
        return new ArrayList<>(keywordsSet);
    }
}
