package TextSent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class Data {

    /**
     * This package-private class consists exclusively of static and private methods.
     * Methods read all necessary data from csv and txt files for string comparison and feature extraction
     * Methods use class ReadFile to read CSV and TXT files
     */

    static ArrayList<String> positiveWords = getPositiveWords();
    static ArrayList<String> negativeWords = getNegativeWords();
    static ArrayList<String> positiveSmileys = getPositiveSmileys();
    static ArrayList<String> negativeSmileys = getNegativeSmileys();
    static ArrayList<String> neutralSmileys = getNeutralSmileys();
    static ArrayList<String> positiveExclamations = getPositiveExclamations();
    static ArrayList<String> negativeExclamations = getNegativeExclamations();
    static ArrayList<String> negations = getNegations();
    static String stopwords = getStopwords();
    static Map<String, String> acronyms = getAcronyms();
    static ArrayList<String> adjectives = getAdjectives();
    static ArrayList<String> adjectivesComparatives = getAdjectivesComparatives();
    static ArrayList<String> adjectivesSuperlatives = getAdjectivesSuperlatives();
    static ArrayList<String> pronouns = getPronouns();

    /**
     * @return returns positive words read from CSV file positiveWords
     */

    private static ArrayList<String> getPositiveWords() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\positiveWords.csv", 0, ';');
    }

    /**
     * @return returns negative words read from CSV file negativeWords
     */

    private static ArrayList<String> getNegativeWords() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\negativeWords.csv", 0, ';');
    }

    /**
     * @return returns positive smileys read from CSV file positiveSmileys
     */

    private static ArrayList<String> getPositiveSmileys() {
        return ReadFile.txtFile("src\\TextSent\\Dependencies\\Files\\positiveSmileys.txt");
    }

    /**
     * @return returns negative smileys read from CSV file negativeSmileys
     */

    private static ArrayList<String> getNegativeSmileys() {
        return ReadFile.txtFile("src\\TextSent\\Dependencies\\Files\\negativeSmileys.txt");
    }

    /**
     * @return returns neutral smileys read from CSV file neutralSmileys
     */

    private static ArrayList<String> getNeutralSmileys() {
        return ReadFile.txtFile("src\\TextSent\\Dependencies\\Files\\neutralSmileys.txt");
    }

    /**
     * @return returns positive exclamations read from CSV file positiveExclamations
     */

    private static ArrayList<String> getPositiveExclamations() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\positiveExclamations.csv", 0, ';');
    }

    /**
     * @return returns negative exclamations read from CSV file negativeExclamations
     */

    private static ArrayList<String> getNegativeExclamations() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\negativeExclamations.csv", 0, ';');
    }

    /**
     * @return returns negations read from CSV file negations
     */

    private static ArrayList<String> getNegations() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\negations.csv", 0, ';');
    }

    /**
     * @return returns stop words read from CSV file stopWords
     */

    private static String getStopwords() {
        ArrayList<String> stopwords = ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\stopWords.csv", 0, ';');
        return stopwords.stream().collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
    }

    /**
     * @return returns acronyms and their full written form read from CSV file acronyms
     * results are saved in data structure Hash map
     */

    private static Map<String, String> getAcronyms() {
        Map<String, String> acronyms = new HashMap<>();
        ArrayList<String> acronym = ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\acronyms.csv", 0, ';');
        ArrayList<String> explanation_acronym = ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\acronyms.csv", 1, ';');
        for (int i = 0; i < acronym.size(); i++) {
            if (acronym.get(i).toLowerCase().length() > 1) { // only get acronyms that are longer than 1
                acronyms.put(acronym.get(i).toLowerCase(), explanation_acronym.get(i).toLowerCase());
            }
        }
        return acronyms;
    }

    /**
     * @return returns adjectives read from CSV file adjectives
     */

    private static ArrayList<String> getAdjectives() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\adjectives.csv", 0, ';');
    }

    /**
     * @return returns adjective comparatives read from CSV file adjectivesComparatives
     */

    private static ArrayList<String> getAdjectivesComparatives() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\adjectivesComparatives.csv", 0, ';');
    }

    /**
     * @return returns adjective superlatives read from CSV file adjectivesSuperlatives
     */

    private static ArrayList<String> getAdjectivesSuperlatives() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\adjectivesSuperlatives.csv", 0, ';');
    }

    /**
     * @return returns pronouns read from CSV file pronouns
     */

    private static ArrayList<String> getPronouns() {
        return ReadFile.csvFile("src\\TextSent\\Dependencies\\Files\\pronouns.csv", 0, ';');
    }

}
