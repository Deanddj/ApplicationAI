package org.app.applicationai;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResourceSelector {

    public static void main(String[] args) {
        // Test de searchDocumentation methode
        String jsonFilePath = "src/main/resources/org/app/applicationai/resourceSelector.json";
        List<String> searchKeywords = List.of("java");
        String result = searchDocumentation(jsonFilePath, searchKeywords);
        System.out.println(result);
    }

    public static String startResourceSelector(List<String> keywords) {
        // Pad naar het JSON-bestand
        String jsonFilePath = "src/main/resources/org/app/applicationai/resourceSelector.json";
        System.out.println("de doorgegeven keywords in ResourceSelector zijn:" + keywords);
        return searchDocumentation(jsonFilePath, keywords);
    }

    public static String searchDocumentation(String jsonFilePath, List<String> searchKeywords) {
        StringBuilder result = new StringBuilder();

        // Laad JSON-bestand
        try (InputStream is = new FileInputStream(jsonFilePath)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray allDocumentation = jsonObject.getJSONArray("foundDocumentation");

            // Sla passende documentatie op
            List<JSONObject> matchedDocumentation = new ArrayList<>();

            // Zoek en verzamel documentatie op basis van trefwoorden
            for (int i = 0; i < allDocumentation.length(); i++) {
                JSONObject doc = allDocumentation.getJSONObject(i);
                JSONArray keywords = doc.getJSONArray("keywords");

                // Controleer of een van de zoekwoorden aanwezig is in de trefwoordenarray
                for (String searchKeyword : searchKeywords) {
                    for (int j = 0; j < keywords.length(); j++) {
                        String keyword = keywords.getString(j).toLowerCase();
                        if (searchKeyword.equalsIgnoreCase(keyword)) {
                            matchedDocumentation.add(doc.getJSONObject("documentation"));
                            break;
                        }
                    }
                }
            }

            // Voeg alle passende documentatiebeschrijvingen samen tot één string
            for (JSONObject documentation : matchedDocumentation) {
                JSONArray keys = documentation.names();
                for (int i = 0; i < keys.length(); i++) {
                    String key = keys.getString(i);
                    if (documentation.get(key) instanceof JSONObject) {
                        JSONObject innerObj = documentation.getJSONObject(key);
                        JSONArray innerKeys = innerObj.names();
                        for (int j = 0; j < innerKeys.length(); j++) {
                            String innerKey = innerKeys.getString(j);
                            if (innerKey.equals("description")) {
                                result.append(innerObj.getString(innerKey)).append(" ");
                            }
                        }
                    } else if (documentation.get(key) instanceof JSONArray) {
                        JSONArray array = documentation.getJSONArray(key);
                        for (int k = 0; k < array.length(); k++) {
                            JSONObject arrayObj = array.getJSONObject(k);
                            JSONArray arrayObjKeys = arrayObj.names();
                            for (int l = 0; l < arrayObjKeys.length(); l++) {
                                String arrayObjKey = arrayObjKeys.getString(l);
                                if (arrayObjKey.equals("description")) {
                                    result.append(arrayObj.getString(arrayObjKey)).append(" ");
                                }
                            }
                        }
                    } else if (key.equals("description")) {
                        result.append(documentation.getString(key)).append(" ");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Bestand niet gevonden: " + jsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString().trim();
    }


    public static List<String> getKeywords(String jsonFilePath) {
        Set<String> keywordsSet = new HashSet<>();

        // Laad JSON-bestand
        try (InputStream is = new FileInputStream(jsonFilePath)) {
            JSONTokener tokener = new JSONTokener(is);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray allDocumentation = jsonObject.getJSONArray("foundDocumentation");

            // Verzamel alle unieke trefwoorden
            for (int i = 0; i < allDocumentation.length(); i++) {
                JSONObject doc = allDocumentation.getJSONObject(i);
                JSONArray keywords = doc.getJSONArray("keywords");

                for (int j = 0; j < keywords.length(); j++) {
                    keywordsSet.add(keywords.getString(j).toLowerCase());
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Bestand niet gevonden: " + jsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Zet Set om naar List
        return new ArrayList<>(keywordsSet);
    }
}
