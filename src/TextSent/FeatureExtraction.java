package TextSent;

import java.util.ArrayList;
import java.util.List;

/**
 * This package-private class consists exclusively of static and (package-)private methods.
 * FeatureExtraction is designed to transform text features into a feature vector with double values.
 * <p>
 * It does this by counting all positive, negative and neutrals text characteristics. Also negations are counted.
 * For subjectivity, adjective and subjective words are counted.
 * <p>
 * To expand the feature vector, some methods in class FeatureClassification must also be changed, as they depend.
 * Some steps in methods are explained with comments.
 */

class FeatureExtraction {

    /**
     * @param text                   cleaned and transformed text to extract features from
     * @param textWordsSplitAllPunc  all separate words from text splitted by all punctuations
     * @param textWordsSplitDotComma all separate words from text splitted by commas and dots.
     * @param exclusions             string list of words to skip when counting features
     * @return vector with counting positive, negative and negation features
     */

    static double[] polarityFeatures(String text, String[] textWordsSplitAllPunc, String[] textWordsSplitDotComma,
                                     List<String> exclusions) {
        // initialize empty double list / vector
        double[] vector = new double[7];

        // positive features
        vector[0] = countWords(Data.positiveWords, textWordsSplitAllPunc, exclusions);
        vector[1] = countWords(Data.positiveSmileys, textWordsSplitDotComma, exclusions);
        vector[2] = countWords(Data.positiveExclamations, textWordsSplitAllPunc, exclusions);
        // negative features
        vector[3] = countWords(Data.negativeWords, textWordsSplitAllPunc, exclusions);
        vector[4] = countWords(Data.negativeSmileys, textWordsSplitDotComma, exclusions);
        vector[5] = countWords(Data.negativeExclamations, textWordsSplitAllPunc, exclusions);
        // negations features
        vector[6] = countWords(Data.negations, textWordsSplitAllPunc, exclusions);

        return vector;
    }

    /**
     * @param textWordsSplitAllPunc all separate words from text splitted by all punctuations
     * @param exclusions            string list of words to skip when counting features
     * @return vector with counting pronouns and adjective features
     */

    static double[] subjectivityFeatures(String[] textWordsSplitAllPunc, List<String> exclusions) {
        // initialize empty double list / vector
        double[] vector = new double[4];

        // count pronouns
        vector[0] = countWords(Data.pronouns, textWordsSplitAllPunc, exclusions);
        // count adjectives
        vector[1] = countWords(Data.adjectives, textWordsSplitAllPunc, exclusions);
        // count adjectivesComparatives
        vector[2] = countWords(Data.adjectivesComparatives, textWordsSplitAllPunc, exclusions);
        // count adjectivesSuperlatives
        vector[3] = countWords(Data.adjectivesSuperlatives, textWordsSplitAllPunc, exclusions);

        return vector;
    }

    /**
     * @param list       list of words for comparison to text words (positive, negative, etc.)
     * @param words      text split into words for comparison to words lists
     * @param exclusions list of words that must be excluded from analysis, for they might influence the results
     * @return count word from given is found in text words
     */

    private static int countWords(ArrayList<String> list, String[] words, List<String> exclusions) {
        if (exclusions == null) {
            // count similarities between word lists
            int count = 0;
            for (String i : list) {
                for (String j : words) {
                    if (i.equals(j)) {
                        count += 1;
                    }
                }
            }
            return count;
        } else {
            exclusions.replaceAll(String::toLowerCase);
            // count similarities between word lists
            int count = 0;
            for (String i : list) {
                for (String j : words) {
                    if (i.equals(j) && !exclusions.contains(j)) {
                        count += 1;
                    }
                }
            }
            return count;
        }
    }
}
