package TextSent;

import java.util.*;

/**
 * Class TextSent creates object from String text to analyse its polarity and/or subjectivity
 * This public class consists of static, (package-)private and public methods. Its the 'main' class of this library
 * <p>
 * TextSent was created to perform text analysis purely based on a lexicon approach.
 * Without any machine learning usage accuracies between approx. 60-90% can be obtained.
 * If accuracy is low, this mostly is due to implicit congruity, multi-polarity or sarcasm.
 * <p>
 * This class methods as a constructor and blueprint for the lexicon based polarity algorithm.
 * This library is designed in such a way, that all other classes are controlled from class TextSent
 * Its also the only class that uses object oriented methods: getPolarity and getSubjectivity.
 * <p>
 * These methods mirror the algorithms used for text analysis. For both methods a double value is calculated.
 * For exclusion excessive feature counts vector normalization is used.
 * <p>
 * For word and smiley recognition String this.text should be split into word lists.
 * For these purposes, two static methods are written. For smileys text is splitted into commas and dots.
 * To compare words, a word list is created splitting text on all punctuations.
 * <p>
 * At last this class contains some static methods to process and/or count datasets and polarity results.
 * Also accuracy of analysis can be calculated if comparison material is present.
 */


public class TextSent {
    private String text;
    private List<String> exclusions;

    /**
     * @param text       Constructor to create object from class TextSent
     * @param exclusions words that must be excluded from analysis, for they might influence the results
     */

    public TextSent(String text, List<String> exclusions) {
        this.text = text.toLowerCase();
        this.exclusions = exclusions;
    }


    /**
     * @return calculated polarity on String this.text
     * <p>
     * This method is the mainly used Object oriented method.
     * It reflects the steps in this text analysis algorithm.
     * this method returns a double polarity value between -1 and 1,
     * where 0 represents neutral, -1 represents negative and 1 represents positive
     */

    public double getPolarity() {
        // clean text
        String cleaned_text = FeatureSelection.cleanText(text);

        // transform text
        String transformed_text = FeatureTransformation.textReplacements(cleaned_text,
                getTextWordsSplitAllPunc(cleaned_text));

        // feature extraction from text into a feature vector
        double[] features = FeatureExtraction.polarityFeatures(transformed_text, getTextWordsSplitAllPunc(transformed_text),
                getTextWordsSplitDotComma(transformed_text), exclusions);

        // text classification; return calculated polarity value
        return FeatureClassification.classifyPolarity(features);

    }

    /**
     * @return calculated subjectivity on String this.text
     * <p>
     * this method is used to calculate subjectivity of text, for subjectivity can also influence text analysis results.
     * It is still in development so its not really sophisticated.
     * I should, however give a sneak peak in how subjective analysed text is.
     * <p>
     * this method returns a double subjectivity value between 0 and 1,
     * where 0 means not subjective and 1 very subjective
     */

    public double getSubjectivity() {
        // text selection
        String cleaned_text = FeatureSelection.cleanText(text);

        // text transformation
        String transformed_text = FeatureTransformation.textReplacements(cleaned_text,
                getTextWordsSplitAllPunc(cleaned_text));

        // get feature vector for counted subjective words, text extraction
        double[] features = FeatureExtraction.subjectivityFeatures(getTextWordsSplitAllPunc(transformed_text), exclusions);

        // return average of features, text classification
        return FeatureClassification.classifySubjectivity(features);
    }

    /**
     * @param text String this.text, the required constructor value for this class
     * @return creates list of words from this.text
     * <p>
     * splits words only on dot and comma, and creates a String word list
     */

    private static String[] getTextWordsSplitDotComma(String text) {
        return text.split("(\\s?<=[,.])\\s|(?=[,.])|\\s+");
    }


    /**
     * @param text String this.text, the required constructor value for this class
     * @return creates list of words from this.text
     * <p>
     * splits words on all punctuations, and creates a String word list
     */

    private static String[] getTextWordsSplitAllPunc(String text) {
        return text.split("[\\p{Punct}\\s]+");
    }

    /**
     * @param dataset    given ArrayList with String texts to analyse
     * @param exclusions words that must be excluded from analysis
     * @return method to get polarities from datasets
     * <p>
     * This method can be used to analyse data sets by giving a String Arraylist with texts as input
     */

    public static Map<String, Double> getPolaritiesDataset(ArrayList<String> dataset, List<String> exclusions) {
        Map<String, Double> classifiedTestDataset = new HashMap<>();
        // for each text, get polarity and put it in map classifiedTestDataset
        for (String data : dataset) {
            TextSent tweetPoll = new TextSent(data, exclusions);
            classifiedTestDataset.put(data, tweetPoll.getPolarity());
        }
        // return map with texts and polarities
        return classifiedTestDataset;
    }

    /**
     *
     * @param map map that need sorting
     * @return map sorted on map values ascending
     *
     * this method sorts Hash map on (polarity) values ascending
     */

    public static Map<String, Double> sortMap(Map<String, Double> map) {
        Map<String, Double> list = map;
        List<Map.Entry<String, Double>> sorted = new ArrayList<>(list.entrySet());
        sorted.sort(Map.Entry.comparingByValue());
        map = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : sorted) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     *
     * @param map      map with texts and corresponding polarity value
     * @param polarity given polarity, three options: positive, negative and neutral
     * @param limit    limit for number of tweets to get
     * @param sort     sort on polarity value ascending, true is sort, false is dont sort
     * @return get x number of texts with given polarity
     * <p>
     * This method retrieves x number of analysed texts with requested polarity.
     * if values should be sorted first in ascending order, argument sort must be set true
     */


    public static ArrayList<String> getSampleTexts(Map<String, Double> map, String polarity, int limit, boolean sort) {
        ArrayList<String> texts = new ArrayList<>();
        int i = 0;

        if (sort) {
            Map<String, Double> list = map;
            List<Map.Entry<String, Double>> sorted = new ArrayList<>(list.entrySet());
            sorted.sort(Map.Entry.comparingByValue());
            map = new LinkedHashMap<>();
            for (Map.Entry<String, Double> entry : sorted) {
                map.put(entry.getKey(), entry.getValue());
            }
        }

        if (polarity.equals("positive")) {
            for (Map.Entry<String, Double> text : map.entrySet()) {
                if (text.getValue() > 0) {
                    texts.add(text.getKey());
                    i++;
                    if (i > limit) break;
                }
            }
        }

        if (polarity.equals("negative")) {
            for (Map.Entry<String, Double> text : map.entrySet()) {
                if (text.getValue() < 0) {
                    texts.add(text.getKey());
                    i++;
                    if (i > limit) break;
                }
            }
        }

        if (polarity.equals("neutral")) {
            for (Map.Entry<String, Double> text : map.entrySet()) {
                if (text.getValue() == 0) {
                    texts.add(text.getKey());
                    i++;
                    if (i > limit) break;
                }
            }
        }

        return texts;
    }


    /**
     * @param classifiedDataset dataset of classified texts
     * @return return int list with positives, negatives and neutrals
     * <p>
     * counts positive, negative and neutrals polarities on given input map with results of analysis
     */

    public static int[] countClassifiedPolarities(Map<String, Double> classifiedDataset) {
        int positive = 0;
        int negative = 0;
        int neutral = 0;

        for (Map.Entry<String, Double> string : classifiedDataset.entrySet()) {

            double polarity = string.getValue();
            if (polarity > 0) positive += 1;
            if (polarity < 0) negative += 1;
            if (polarity == 0) neutral += 1;

        }

        // save positives, negatives and neutrals to int list
        int[] polarities = new int[3];
        polarities[0] = positive;
        polarities[1] = negative;
        polarities[2] = neutral;

        // return int list containing positives, negatives and neutrals counts
        return polarities;
    }


    /**
     * @param x first list with positives, negatives and neutral counted polarities
     * @param y second list with positives, negatives and neutral counted polarities
     * @return calculates accuracy by calculating similarity of 2 given lists
     * <p>
     * calculates accuracy of overall results.
     * Can only be calculated if comparison data is available.
     * Thus x are the results of analysis (counted polarities) and y is a list of comparison data (counted polarities)
     */

    public static double calculateAccuracy(int[] x, int[] y) {
        double accuracy = 0;
        for (int i = 0; i < x.length; i++) {
            accuracy += (double) Math.min(x[i], y[i]) / (double) Math.max(x[i], y[i]);
        }
        System.out.println("Positive accuracy: "+(double) Math.min(x[0], y[0]) / (double) Math.max(x[0], y[0]) * 100.0+"%");
        System.out.println("Negative accuracy: "+(double) Math.min(x[1], y[1]) / (double) Math.max(x[1], y[1]) * 100.0+"%");
        System.out.println("Neutral accuracy: "+(double) Math.min(x[2], y[2]) / (double) Math.max(x[2], y[2]) * 100.0+"%");
        return Math.round(((accuracy / x.length) * 100) * 100.0) / 100.0;
    }


    /**
     * @param polarity polarity value for analysed text
     * @return converts double polarity value into String positive, negative or neutral
     */

    public static String tagPolarity(double polarity) {
        if (polarity > 0) return "positive";
        else if (polarity < 0) return "negative";
        else return "neutral";
    }
}
