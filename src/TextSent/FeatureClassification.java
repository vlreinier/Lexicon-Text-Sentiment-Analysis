package TextSent;

/**
 * This package-private class consists exclusively of static and (package-)private methods.
 * This class represents the last step in the lexicon based text analysis algorithm.
 *
 * Methods in this class create polarity and subjectivity outputs for the algorithm.
 * From double feature vectors, double output values are created.
 *
 * These values are determined by some if statements.
 * Feature vectors are normalized before final determination to ensure extreme values dont mess up results.
 */

class FeatureClassification {

    /**
     * @param vector given input vector which was generated in class Feature Extraction
     * @return final classified double value for polarity
     *
     * this method calculates final polarity double value.
     * it does so by subtracting negatives from positives
     *
     * Also it will check for negations, and negate if necessary.
     * Negation of polarity value is performed by multiplying polarity with -0.5.
     * this is a approach similar to python library TextBlob
     */

    static double classifyPolarity(double[] vector) {
        // first check how many positive and negatives values are found, so negations do not ruin results
        if (Math.abs((vector[0] + vector[1] + vector[2]) - (vector[3] + vector[4] + vector[5])) > 1) vector[6] = 0;

        // normalize vector
        double[] features = normalizeVector(vector);

        // positive features
        double positives = features[0] + features[1] + features[2];
        // negative features
        double negatives = features[3] + features[4] + features[5];
        // else subtract negative from positive
        double polarity = positives - negatives;
        // negations
        double negations = features[6];


        // if only one positive word and negations are present, negate once
        if (positives == 1 && negatives == 0 && negations > 0) polarity *= -0.5;
        // if only one negative word and negations are present, negate once
        else if (negatives == 1 && positives == 0 && negations > 0) polarity *= -0.5;

        // dealing with multi-polarity | if negative and positive counts are the same, text is considered neutral
        if (negatives==positives) polarity = 0;

        // return calculated polarity value
        return polarity;
    }


    /**
     * @param vector given input feature vector which was generated in class Feature Extraction
     * @return final classified double value for subjectivity
     *
     * this method calculates final subjectivity double value.
     */

    static double classifySubjectivity(double[] vector) {
        double[] features = normalizeVector(vector);

        // sum of normalized features
        double sum = intSum(features);
        // count of non zero features
        double count_nonzero = nonZero(features);

        if (sum > 1) return 1.0;
        return sum / count_nonzero;
    }

    /**
     * @param features given input feature vector which was generated in class Feature Extraction
     * @return normalized feature vector
     * <p>
     * this method normalizes all values in given input vector by comparing
     */

    private static double[] normalizeVector(double[] features) {
        double length = 0;
        for (int i = 0; i < features.length - 1; i++) {
            length += Math.sqrt(features[i] * features[i]);
        }
        for (int i = 0; i < features.length - 1; i++) {
            if (features[i] != 0) {
                features[i] = features[i] / length;
            }
        }

        return features;
    }

    /**
     * @param polarity current calculated polarity value
     * @param negate   x times polarity value must be negated
     * @return negated polarity value
     *
     * this method can be used to negate calculated polarity x number of times.
     */

    private static double negation(double polarity, double negate) {
        negate = (int) negate;
        for (int i = 0; i < negate; i++) {
            polarity *= -0.5;
        }
        return polarity;
    }

    /**
     * @param features given input feature vector which was generated in class Feature Extraction
     * @return sum of al values in input vector
     *
     * this method calculates sum of given input vector with double values
     */

    private static double intSum(double[] features) {
        double sum = 0.0;
        for (double i : features) {
            sum += i;
        }
        return sum;
    }

    /**
     * @param features given input feature vector which was generated in class Feature Extraction
     * @return count of non zero values in input vector
     *
     * this method counts all non zero values in given input vector
     */

    private static double nonZero(double[] features) {
        double count = 0.0;
        for (double i : features) {
            if (i > 0.0) count += 1.0;
        }
        return count;
    }

}
