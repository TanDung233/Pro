package main.java.utility.csv;

import main.java.data.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Handles loading and saving collections of Person objects to/from CSV files.
 */
public class CSVProcess {
    private String filePath;
    private TreeSet<Person> persons;
    private final CSVReader csvReader;
    private final CSVWriter csvWriter;

    public CSVProcess(CSVReader csvReader, CSVWriter csvWriter) {
        this.csvReader = csvReader;
        this.csvWriter = csvWriter;
    }

    /**
     * Gets the current file path
     * @return The path to the CSV file
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path for operations
     * @param filePath The path to the CSV file
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the current collection
     * @return The collection of persons
     */
    public TreeSet<Person> getPersons() {
        return persons;
    }

    /**
     * Sets the collection to work with
     * @param persons The collection of persons
     */
    public void setPersons(TreeSet<Person> persons) {
        this.persons = persons;
    }

    /**
     * Loads collection from CSV file
     * @param fileName The file to load from
     * @return TreeSet of parsed Person objects
     * @throws IllegalArgumentException If CSV format is invalid
     */
    public TreeSet<Person> loadCollection(String fileName) throws IllegalArgumentException {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        this.filePath = fileName;

        try {
            List<String> lines = csvReader.readFromFile(fileName);
            TreeSet<Person> loadedPersons = new TreeSet<>();
            boolean isFirstLine = true;

            for (String line : lines) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                loadedPersons.add(parsePerson(line));
            }

            this.persons = loadedPersons;
            return loadedPersons;

        } catch (Exception e) {
            throw new IllegalArgumentException("CSV format error: " + e.getMessage(), e);
        }
    }

    private Person parsePerson(String csvLine) {
        String[] elements = csvLine.split(",");
        return new Person(
                Integer.parseInt(elements[0]),  // id
                elements[1],                    // name
                new Coordinates(                // coordinates
                        Integer.parseInt(elements[2]),
                        Double.parseDouble(elements[3])
                ),
                Integer.parseInt(elements[5]),  // height
                LocalDateTime.parse(elements[6], DateTimeFormatter.ISO_LOCAL_DATE_TIME), // birthday
                Color.valueOf(elements[7]),    // eyeColor
                Color.valueOf(elements[8]),    // hairColor
                new Location(                 // location
                        Integer.parseInt(elements[2]),
                        Double.parseDouble(elements[3]),
                        Double.parseDouble(elements[4]),
                        elements[9]
                )
        );
    }

    /**
     * Writes collection to CSV file
     * @param persons Collection to save
     * @throws IllegalArgumentException If file path not set
     */
    public void writeCollection(TreeSet<Person> persons) throws IllegalArgumentException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path not specified");
        }

        this.persons = persons;

        String[] headers = {
                "id", "name", "x", "y", "z", "height", "birthday",
                "eyeColor", "hairColor", "locationName"
        };

        List<String> records = new ArrayList<>();
        for (Person person : persons) {
            records.add(convertToCSV(person));
        }

        csvWriter.writeToFile(filePath, headers, records);
    }

    private String convertToCSV(Person person) {
        return String.join(",",
                String.valueOf(person.getId()),
                person.getName(),
                String.valueOf(person.getCoordinates().getX()),
                String.valueOf(person.getCoordinates().getY()),
                String.valueOf(person.getLocation().getZ()),
                String.valueOf(person.getHeight()),
                person.getBirthday().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                person.getEyeColor().name(),
                person.getHairColor().name(),
                person.getLocation().getName()
        );
    }
}