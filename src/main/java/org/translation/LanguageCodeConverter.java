package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    private Map<String, String> languageMap = new HashMap<>();
    private Map<String, String> reverseLanguageMap = new HashMap<>();

    // TODO Task: pick appropriate instance variables to store the data necessary for this class

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // Use an iterator to go through the lines and skip the header
            Iterator<String> iterator = lines.iterator();
            if (iterator.hasNext()) {
                iterator.next(); // Skip the header
            }

            // Populate the languageMap with code as key and name as value
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] parts = line.split("\t"); // Split on tab
                if (parts.length == 2) {
                    String language = parts[0].trim();
                    String code = parts[1].trim();
                    languageMap.put(code, language);
                    reverseLanguageMap.put(language, code); // Reverse map for fromLanguage lookup
                }
            }

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // TODO Task: update this code to use your instance variable to return the correct value
        return languageMap.getOrDefault(code, "Unknown code");
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // TODO Task: update this code to use your instance variable to return the correct value
        return reverseLanguageMap.getOrDefault(language, "Unknown language");
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // TODO Task: update this code to use your instance variable to return the correct value
        return languageMap.size();
    }
}
