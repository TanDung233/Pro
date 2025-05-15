package Server.Utility.Csv;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The CSVReader class provides methods for reading the contents of a CSV file.
 * It supports reading the file and returning its contents as a list of lines.
 */
public class CSVReader {

    /**
     * Reads the contents of a CSV file and returns them as a list of lines.
     *
     * @param pathToFile The path to the CSV file.
     * @return A list of lines read from the CSV file.
     * @throws IllegalArgumentException If there is an error reading the file.
     */
    public ArrayList<String> readFromFile(String pathToFile) {
        ArrayList<String> lineList = new ArrayList<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(pathToFile))) {
            StringBuilder line = new StringBuilder();
            int data;
            while ((data = inputStreamReader.read()) != -1) {
                char character = (char) data;
                if (character == '\n') {
                    lineList.add(line.toString().trim());
                    line.setLength(0);
                } else {
                    line.append(character);
                }
            }
            if (line.length() > 0) {
                lineList.add(line.toString().trim());
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("CSV format violation: " + exception.getMessage());
        }
        return lineList;
    }

    /**
     * Returns the value of the flag.
     *
     * @return The value of the flag.
     */
}