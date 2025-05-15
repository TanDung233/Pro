package main.java.utility.csv;

import main.java.data.*;
import main.java.utility.CollectionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class provides methods for loading and saving collections of Person objects to CSV files.
 */
public class CSVProcess {
    private static String pathToFile;
    private static TreeSet<Person> persons;

    /**
     * Retrieves the path to the CSV file.
     *
     * @return The path to the CSV file.
     */
    public static String getPathToFile() {
        return pathToFile;
    }

    /**
     * Sets the path to the CSV file.
     *
     * @param pathToFile The path to the CSV file.
     */
    public static void setPathToFile(String pathToFile){
        CSVProcess.pathToFile = pathToFile;
    }

    /**
     * Sets the collection of persons.
     *
     * @param persons The collection of persons.
     */
    public static void setCollection(TreeSet<Person> persons) {
        CSVProcess.persons = persons;
    }

    /**
     * Loads the collection from the specified CSV file.
     *
     * @param fileName The name of the CSV file to load the collection from.
     * @return The collection of persons loaded from the CSV file.
     * @throws IllegalArgumentException If there is an error in the CSV format.
     */
    public static TreeSet<Person> loadCollection(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("File name has not been provided!");
        } else {
            try {
                List<String> parsedCSVFile = CSVReader.readFromFile(fileName);
                CollectionManager.initializationCollection();
                TreeSet<Person> persons = new TreeSet<>();
                boolean isFirstLine = true;
                for (String line : parsedCSVFile) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] elements = line.split(",");
                    int id = Integer.parseInt(elements[0]);
                    String name = elements[1];
                    Integer x = Integer.valueOf(elements[2]);
                    Double y = Double.valueOf(elements[3]);
                    Double z = Double.valueOf(elements[4]);
                    Coordinates coordinates = new Coordinates(x, y);
                    int height = Integer.parseInt(elements[5]);
                    LocalDateTime birthday = LocalDateTime.parse(elements[6], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    Color eyeColor = Color.valueOf(elements[7]);
                    Color hairColor = Color.valueOf(elements[8]);
                    String locationName = elements[9];
                    Location location = new Location(x, y, z, locationName);
                    Person person = new Person(id, name, coordinates, height, birthday, eyeColor, hairColor, location);
                    persons.add(person);
                }
                CSVProcess.persons = persons;
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("CSV Format Violation!: " + exception.getMessage());
            }
        }
        return persons;
    }

    /**
     * Writes the collection to the CSV file.
     *
     * @throws IllegalArgumentException If there is an error in the CSV format.
     */
    public static void writeCollection() {
        String[] headers = {
                "id", "name", "x", "y", "z", "height", "birthday",
                "eyeColor", "hairColor", "locationName"
        };
        List<String> records = new ArrayList<>();

        for (Person person : CollectionManager.getCollection()) {
            String[] fields = {
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
            };
            records.add(String.join(",", fields));
        }

        CSVWriter.writeToFile(pathToFile, headers, records);
    }
}