package Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Group;

import java.util.*;


public class InteractivePieChart extends javafx.application.Application {
    private static double positive, negative, neutral;
    private static String title;
    private static Map<String, Double> results;
    private static GridPane gridpane;


    /**
     * @param positive number of positives texts
     * @param negative number of negative texts
     * @param neutral  number of neutral texts
     * @param title    title for chart
     *                 launches bar chart with given positive, negative and neutral counts
     */

    public static void launchGrid(Map<String, Double> results, double positive, double negative, double neutral, String title) {
        InteractivePieChart.positive = positive;
        InteractivePieChart.negative = negative;
        InteractivePieChart.neutral = neutral;
        InteractivePieChart.title = title;
        InteractivePieChart.results = results;
        javafx.application.Application.launch(InteractivePieChart.class);
    }


    /**
     * @param stage used to run/stage chart application (build-in java library)
     *              application setup
     */

    @Override
    public void start(Stage stage) {
        stage.setTitle("Polarity Chart");
        stage.setWidth(1500);
        stage.setHeight(900);

        // create pie slices and add to observable list
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new javafx.scene.chart.PieChart.Data((int) positive + " Positive", positive),
                        new javafx.scene.chart.PieChart.Data((int) negative + " Negative", negative),
                        new javafx.scene.chart.PieChart.Data((int) neutral + " Neutral", neutral));
        // create pie chart, set line length, set title and remove legend
        final javafx.scene.chart.PieChart chart = new javafx.scene.chart.PieChart(pieChartData);
        chart.setLabelLineLength(10);
        chart.setLegendVisible(false);
        chart.setTitle(title);
        // apply custom colors
        applyCustomColorSequence(pieChartData, "green", "red", "orange");

        // Root element
        Scene scene = new Scene(new Group());
        BorderPane pane = new BorderPane();

        for (final javafx.scene.chart.PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            if (data.getPieValue() == positive) {
                                gridpane = getPositives();
                                pane.setBottom(gridpane);
                            }
                            if (data.getPieValue() == negative) {
                                gridpane = getNegatives();
                                pane.setBottom(gridpane);
                            }
                            if (data.getPieValue() == neutral) {
                                gridpane = getNeutrals();
                                pane.setBottom(gridpane);
                            }
                        }
                    });
        }

        pane.setTop(chart); pane.setBottom(getPositives());

        ((Group) scene.getRoot()).getChildren().add(pane);
        // add chart to scene group
        // show chart
        stage.setScene(scene);
        stage.show();
    }


    public static GridPane getPositives(){
        // new empty gridpane
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(0,30,0,50));

        // get positive keys (texts) and add to list
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Double> result : results.entrySet()) {
            if (result.getValue() > 0) {
                keys.add(result.getKey());
            }
        }

        // add new random text labels to gridpane
        for (int i = 0; i < 5; i++) {
            String key = keys.get(new Random().nextInt(keys.size()));
            Label label = new Label(key+"\n\n");
            label.setWrapText(true);
            label.setMaxWidth(1350);
            label.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 13; -fx-text-fill: green;");
            pane.add(label,1, i);
        }
        return pane;
    }

    public static GridPane getNegatives(){
        // new empty gridpane
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(0,30,0,50));

        // get positive keys (texts) and add to list
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Double> result : results.entrySet()) {
            if (result.getValue() < 0) {
                keys.add(result.getKey());
            }
        }

        // add new random text labels to gridpane
        for (int i = 0; i < 5; i++) {
            String key = keys.get(new Random().nextInt(keys.size()));
            Label label = new Label(key+"\n\n");
            label.setWrapText(true);
            label.setMaxWidth(1350);
            label.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 13; -fx-text-fill: red;");
            pane.add(label,1, i);
        }
        return pane;
    }

    public static GridPane getNeutrals(){
        // new empty gridpane
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(0,30,0,50));

        // get positive keys (texts) and add to list
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Double> result : results.entrySet()) {
            if (result.getValue() == 0) {
                keys.add(result.getKey());
            }
        }

        // add new random text labels to gridpane
        for (int i = 0; i < 5; i++) {
            String key = keys.get(new Random().nextInt(keys.size()));
            Label label = new Label(key+"\n\n");
            label.setWrapText(true);
            label.setMaxWidth(1350);
            label.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 13; -fx-text-fill: orange;");
            pane.add(label,1, i);
        }
        return pane;
    }

    /**
     * @param pieChartData all chart slices
     * @param pieColors    vararg string with colors
     *                     <p>
     *                     function for setting custom colors to pie slices
     */

    private void applyCustomColorSequence(ObservableList<javafx.scene.chart.PieChart.Data> pieChartData, String... pieColors) {

        int i = 0;
        for (javafx.scene.chart.PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: " + pieColors[i % pieColors.length] + ";");
            i++;
        }
    }
}