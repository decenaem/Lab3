package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TODO Task: pick appropriate instance variables for this class

    private Map<String, Map<String, String>> countryTranslations;
    private Map<String, List<String>> countryLanguages;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        countryTranslations = new HashMap<>();
        countryLanguages = new HashMap<>();

        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryData = jsonArray.getJSONObject(i);
                String countryCode = countryData.getString("alpha3");

                Map<String, String> translations = new HashMap<>();
                List<String> languages = new ArrayList<>();

                for (String key : countryData.keySet()) {
                    if (!key.equals("id") && !key.equals("alpha2") && !key.equals("alpha3")) {
                        translations.put(key, countryData.getString(key));
                        languages.add(key);
                    }
                }
                countryTranslations.put(countryCode, translations);
                countryLanguages.put(countryCode, languages);
            }

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TODO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        if (countryLanguages.containsKey(country)) {
            return new ArrayList<>(countryLanguages.get(country));
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        // TODO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryTranslations.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // TODO Task: complete this method using your instance variables as needed
        if (countryTranslations.containsKey(country) && countryTranslations.get(country).containsKey(language)) {
            return countryTranslations.get(country).get(language);
        }
        return null;
    }
}
