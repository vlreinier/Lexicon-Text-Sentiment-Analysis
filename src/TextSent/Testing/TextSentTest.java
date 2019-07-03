package TextSent.Testing;

import Application.InteractivePieChart;
import TextSent.ReadFile;
import TextSent.TextSent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class TextSentTest {

    /**
     * @param args main class for creating TextSent object and testing its functionality
     */

    public static void main(String[] args) {
        // single string analysis
        // analyse single text and tag its polarity value
        String single_string = "i don't like it";
        TextSent text = new TextSent(single_string, null);
        System.out.println(single_string + " ; polarity " + text.getPolarity() +"="+TextSent.tagPolarity(text.getPolarity()));


        // tweets analysis
        // file path
        String tweets = "src\\TextSent\\Datasets\\tweetsapple.csv";
        // csv to ArrayList String
        ArrayList<String> tweets_dataset = ReadFile.csvFile(tweets, 1, ',');
        // get text polarities
        Map<String, Double> analysed_tweets = TextSent.getPolaritiesDataset(tweets_dataset, null);
        // count classified polarities
        int[] counted_tweets = TextSent.countClassifiedPolarities(analysed_tweets);
        // call GUI to show results
        //InteractivePieChart.launchGrid(analysed_tweets, counted_tweets[0], counted_tweets[1], counted_tweets[2],"Tweet Polarities");


        // reviews analysis
        // file path
        String imdb = "src\\TextSent\\Datasets\\rambo.csv";
        // csv to ArrayList String
        ArrayList<String> reviews_dataset = ReadFile.csvFile(imdb, 1, '~');
        // list to exclude from
        List<String> exclude = Arrays.asList("");
        // get text polarities
        Map<String, Double> analysed_reviews = TextSent.getPolaritiesDataset(reviews_dataset, exclude);
        // count classified polarities
        int[] counted_reviews = TextSent.countClassifiedPolarities(analysed_reviews);
        // read IMDB ratings from CSV file
        ArrayList<String> ratings = ReadFile.csvFile(imdb, 0, '~');
        // count IMDB ratings for comparison
        int[] counted_ratings = countIMDBRatings(ratings, imdb);
        // compare ratings with results and print accuracy
        System.out.println("Overall accuracy compared to analysed reviews: " + TextSent.calculateAccuracy(counted_reviews, counted_ratings)+"%");
        // call GUI to show results
        InteractivePieChart.launchGrid(analysed_reviews, counted_reviews[0], counted_reviews[1], counted_reviews[2],"Movie Reviews Polarity Results");

    }


    private static int[] countIMDBRatings(ArrayList<String> ratings, String filepath) {
        // count ratings
        int positive = 0;
        int negative = 0;
        int neutral = 0;
        for (String rating : ratings) {
            double parsed_rating = Double.parseDouble(rating);
            if (parsed_rating > 5) positive += 1;
            if (parsed_rating < 5) negative += 1;
            if (parsed_rating == 5) neutral += 1;
        }

        // getting filename from path
        String fileName = new File(filepath).getName();
        // print counted star ratings from imdb
        System.out.println("\nRatings for " + fileName + ":");
        System.out.println("Positive ratings: " + positive);
        System.out.println("Negative Ratings: " + negative);
        System.out.println("Neutral Ratings: " + neutral);
        System.out.println();

        // put counted ratings in integer list and return list
        int[] counted = new int[3];
        counted[0] = positive;
        counted[1] = negative;
        counted[2] = neutral;
        return counted;
    }
}
