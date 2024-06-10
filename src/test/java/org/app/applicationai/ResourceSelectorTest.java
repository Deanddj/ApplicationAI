package org.app.applicationai;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceSelectorTest {

    @Test
    void getKeywordTest() {
        List<String> expectedKeywords = new ArrayList<>(Arrays.asList(
                "python", "programming-language", "java", "valorant", "gaming"
        ));
        List<String> actualKeywords = ResourceSelector.getKeywords("src/main/resources/org/app/applicationai/resourceSelector.json");

        // Ensure the actual keywords list contains all expected keywords
        assertTrue(actualKeywords.containsAll(expectedKeywords) && expectedKeywords.containsAll(actualKeywords),
                "The actual keywords list does not match the expected keywords list");
    }

    @Test
    void documentationTest(){
        List<String> expectedKeywords = new ArrayList<>(Arrays.asList("valorant"
        ));
        assertEquals("Ali is niet zo goed in het spel. Als cijfer krijgt hij een 4 van de 10.", ResourceSelector.searchDocumentation("src/main/resources/org/app/applicationai/resourceSelector.json", expectedKeywords));
    }

    @Test
    void documentationTest2(){
        List<String> expectedKeywords = new ArrayList<>(Arrays.asList("java"
        ));
        assertEquals("Ali is niet zo goed in het spel. Als cijfer krijgt hij een 4 van de 10.", ResourceSelector.searchDocumentation("src/main/resources/org/app/applicationai/resourceSelector.json", expectedKeywords));
    }

}
