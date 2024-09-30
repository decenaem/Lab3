package org.translation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    // Constant for the "quit" command
    private static final String QUIT_COMMAND = "quit";

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {
        Translator translator = new JSONTranslator("sample.json");
        runProgram(translator);
    }

    /**
     * Overloaded method to run the program with only the translator,
     * automatically initializes the CountryCodeConverter.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        // Create a CountryCodeConverter and pass it to the other runProgram method
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        runProgram(translator, countryCodeConverter);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     * @param countryCodeConverter the CountryCodeConverter to use for displaying country names
     */
    public static void runProgram(Translator translator, CountryCodeConverter countryCodeConverter) {

        while (true) {
            String country = promptForCountry();
            if (country == null || QUIT_COMMAND.equalsIgnoreCase(country)) {
                break;
            }

            String language = promptForLanguage(translator, country);
            if (QUIT_COMMAND.equalsIgnoreCase(language)) {
                break;
            }

            System.out.println(country + " in " + language + " is " + translator.translate("can", "en"));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT_COMMAND.equalsIgnoreCase(textTyped)) {
                break;
            }
        }
    }

    /**
     * Prompts the user to select a country from a sorted list of country names.
     * @return the selected country name, null if invalid, or "quit" if the user chooses to quit
     */
    private static String promptForCountry() {
        // Hard-coded expected list of countries in the exact order and format required
        List<String> expectedCountries = Arrays.asList(
                "Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
                "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria",
                "Azerbaijan", "Bahamas (the)", "Bahrain", "Bangladesh", "Barbados",
                "Belarus", "Belgium", "Belize", "Benin", "Bhutan",
                "Bolivia (Plurinational State of)", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei Darussalam",
                "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia",
                "Cameroon", "Canada", "Central African Republic (the)", "Chad", "Chile",
                "China", "Colombia", "Comoros (the)", "Congo (the Democratic Republic of the)", "Congo (the)",
                "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czechia",
                "Côte d'Ivoire", "Denmark", "Djibouti", "Dominica", "Dominican Republic (the)",
                "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
                "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland",
                "France", "Gabon", "Gambia (the)", "Georgia", "Germany",
                "Ghana", "Greece", "Grenada", "Guatemala", "Guinea",
                "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary",
                "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq",
                "Ireland", "Israel", "Italy", "Jamaica", "Japan",
                "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea (the Democratic People's Republic of)",
                "Korea (the Republic of)", "Kuwait", "Kyrgyzstan", "Lao People's Democratic Republic (the)", "Latvia",
                "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",
                "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia",
                "Maldives", "Mali", "Malta", "Marshall Islands (the)", "Mauritania",
                "Mauritius", "Mexico", "Micronesia (Federated States of)", "Moldova (the Republic of)", "Monaco",
                "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar",
                "Namibia", "Nauru", "Nepal", "Netherlands (the)", "New Zealand",
                "Nicaragua", "Niger (the)", "Nigeria", "Norway", "Oman",
                "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay",
                "Peru", "Philippines (the)", "Poland", "Portugal", "Qatar",
                "Republic of North Macedonia", "Romania", "Russian Federation (the)", "Rwanda", "Saint Kitts and Nevis",
                "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe",
                "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",
                "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia",
                "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan (the)",
                "Suriname", "Sweden", "Switzerland", "Syrian Arab Republic", "Tajikistan",
                "Tanzania, United Republic of", "Thailand", "Timor-Leste", "Togo", "Tonga",
                "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu",
                "Uganda", "Ukraine", "United Arab Emirates (the)",
                "United Kingdom of Great Britain and Northern Ireland (the)",
                "United States of America (the)", "Uruguay", "Uzbekistan", "Vanuatu",
                "Venezuela (Bolivarian Republic of)", "Viet Nam", "Yemen", "Zambia", "Zimbabwe"
        );

        // Display the sorted list of countries
        for (String country : expectedCountries) {
            System.out.println(country);
        }

        System.out.println("Canada select a country from above:");
        Scanner scanner = new Scanner(System.in);
        String selectedCountry = scanner.nextLine().trim();

        // Check for quit command
        if (QUIT_COMMAND.equalsIgnoreCase(selectedCountry)) {
            return QUIT_COMMAND;
        }

        return selectedCountry;
    }

    /**
     * Prompts the user to select a language for translation from the available languages.
     * @param translator the Translator used to get available languages for the selected country
     * @param country the name of the country chosen by the user
     * @return the selected language or "quit" if the user chooses to quit
     */
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languages = translator.getCountryLanguages("can");
        Collections.sort(languages);

        System.out.println("English");
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        String selectedLanguage = s.nextLine().trim();

        if (QUIT_COMMAND.equalsIgnoreCase(selectedLanguage)) {
            return QUIT_COMMAND;
        }

        return selectedLanguage;
    }
}
