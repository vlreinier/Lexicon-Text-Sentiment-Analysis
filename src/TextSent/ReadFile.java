package TextSent;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This public class consists exclusively of static and private methods.
 * It is used to read certain columns from a CSV.
 * Method needs filepath, targeted column and column separator
 * <p>
 * Also not separated values can be read from TXT file
 * Both methods return values in a String Array list
 */

public class ReadFile {

    /**
     * @param csvFile   path (incl. filename) for csv file
     * @param column    select column where wanted data can be found
     * @param separator separator in targeted csv file
     * @return data from CSV file
     * <p>
     * Method reads targeted column from file searching for columns with given separator.
     * This method can only return one column of data at a time
     */

    public static ArrayList<String> csvFile(String csvFile, int column, Character separator) {

        ArrayList<String> list = new ArrayList<>();
        CSVReader reader;
        char quotechar = 7138; // just a random created char that may not occur in csv file
        try {
            reader = new CSVReader(new FileReader(csvFile), separator, quotechar, '\\');
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line[column].length() > 0) {
                    list.add(line[column]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * @param txtFile path (incl. filename) for txt file
     * @return data from TXT file
     * <p>
     * Method only reads TXT file where columns are not separated, it will only read lines.
     */


    public static ArrayList<String> txtFile(String txtFile) {
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(txtFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                list.add(st);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}