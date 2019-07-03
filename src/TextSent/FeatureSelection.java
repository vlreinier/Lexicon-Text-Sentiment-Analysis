package TextSent;

/**
 * This package-private class consists exclusively of a static and package-private method.
 * <p>
 * This class is used to preprocess and clean a String text.
 * It contains only method cleanText which can delete unnecessary characteristics.
 * Primarily it will remove characters and words. It also replaces double whitespaces and characters with single ones.
 * <p>
 * This class was primarily written for pre-processing tweets, but i can also be used for other texts.
 */

class FeatureSelection {

    /**
     * @param text input String text which needs cleaning and pre-processing
     * @return preprocessed and cleaned String text
     */

    static String cleanText(String text) {

        // remove URL's
        text = text.replaceAll("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", "");

        // remove @username
        text = text.replaceAll("(?<=^|(?<=[^a-zA-Z0-9-_.]))@([A-Za-z]+[A-Za-z0-9]+)", "");

        // words that have no meaning in some specific combinations
        text = text.replaceAll("as well", "");
        text = text.replaceAll("well if", "");
        text = text.replaceAll("well over", "");

        // remove stop words
        text = text.replaceAll(Data.stopwords, "");

        // remove double (or more) whitespaces
        text = text.trim().replaceAll("[\\s]+", " ");

        // 3 or more repeated alphabet characters after each other are removed
        text = text.replaceAll("([a-zA-Z])\\1{2,}", "$1"); // $1$1 for keeping two

        // if positive words are quoted mostly they mean the exact negative opposite
        // but this is not always the case, sometimes its used to only degrade/reduce the expression/meaning
        text = text.replaceAll("(\\s\").*?(\"\\s|\".)", " ");
        text = text.replaceAll("(\\s').*?('\\s|'.)", " ");
        text = text.replaceAll("(\\s`).*?(`\\s|`.)", " ");
        text = text.replaceAll("(\\s\\*).*?(\\*\\s|\\*.)", " ");

        return text;
    }
}
