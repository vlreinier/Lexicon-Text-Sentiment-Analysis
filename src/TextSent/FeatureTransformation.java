package TextSent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This package-private class consists exclusively of static and private methods.
 * This class is created to transform text into usable text for analysis.
 * <p>
 * For now, certain unicode is replaced. Acronyms are replaced with full written forms
 * and hash tag information is retrieved.
 * <p>
 * Methods in this class are together with feature selection part of preprocessing the text for analysis
 * This methods seem to remove and replace, where feature selection only removes characteristics.
 */

class FeatureTransformation {

    /**
     * @param text                  preprocessed/cleaned text to analyse
     * @param textWordsSplitAllPunc text words split by punctuations
     * @return returns transformed text. words and characters are replaced (and added)
     */

    static String textReplacements(String text, String[] textWordsSplitAllPunc) {
        // add full forms of acronyms
        text = FeatureTransformation.replaceAcronyms(text, textWordsSplitAllPunc);

        // checks if any negative or positive words are found in hash tags
        text = FeatureTransformation.hashTag(text);

        // remove html language and replace with actual character or string
        text = text.replaceAll("&lt;", "<");
        text = text.replaceAll("&gt;", ">");
        text = text.replaceAll("&gt;", ">");
        text = text.replaceAll("&gt;", ">");

        return text;
    }


    /**
     * @param text                  preprocessed/cleaned text to analyse
     * @param textWordsSplitAllPunc text words split by punctuations
     * @return acronyms in String text replaced by its full form
     */

    private static String replaceAcronyms(String text, String[] textWordsSplitAllPunc) {
        // find acronyms / abbreviations and add full written forms
        for (String i : textWordsSplitAllPunc) {

            if (Data.acronyms.get(i) != null) {
                text += " " + Data.acronyms.get(i);
            }
        }
        return text;
    }


    /**
     * @param text preprocessed/cleaned text to analyse
     * @return text with removed hash tags and possible added negative or positive words
     * <p>
     * this method checks if positive or negative words are included in a hash tag
     * if so it will add this word separately to String text
     * At last, hash tags including content are removed
     */

    private static String hashTag(String text) {
        // get values from hash tags into list
        Pattern pattern = Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)");
        Matcher match = pattern.matcher(text);
        List<String> tags = new ArrayList<>();
        while (match.find()) tags.add(match.group(1));

        // compare with positive words
        for (String pos : Data.positiveWords) {
            for (String tag : tags) {
                if (tag.contains(pos)) {
                    text += " " + pos;
                    if (tag.contains("not")) text += " not";
                }
            }
        }

        // compare with negative words
        for (String neg : Data.negativeWords) {
            for (String tag : tags) {
                if (tag.contains(neg)) {
                    text += " " + neg;
                    if (tag.contains("not")) text += " not";
                }
            }
        }

        // at last remove hast tags and their content
        return text.replaceAll("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)", " ");
    }
}
