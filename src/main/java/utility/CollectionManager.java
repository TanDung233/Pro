package main.java.utility;

import main.java.data.Person;
import main.java.utility.csv.CSVProcess;
import java.time.ZonedDateTime;
import java.util.*;

import main.java.exceptions.EmptyCollectionException;

public class CollectionManager {

    public static Stack<String> historyCommandList = new Stack<>();
    private static List<Person> clearList = new ArrayList<>();
    private static TreeSet<Person> personTreeSet;
    private static ZonedDateTime initializationDate;

    /**
     * Initializes the collection if it is null.
     */
    public static void initializationCollection() {
        personTreeSet = new TreeSet<>();
        initializationDate = ZonedDateTime.now();
    }

    /**
     * Retrieves the collection of persons.
     *
     * @return The collection of persons.
     */
    public static TreeSet<Person> getCollection() {
        return personTreeSet;
    }

    /**
     * Retrieves a person from the collection based on its ID.
     *
     * @param ID The ID of the person.
     * @return The person with the specified ID, or null if not found.
     */
    public static Person getPersonByID(int ID) {
        for (Person person : personTreeSet) {
            if (person.getId() == ID) {
                return person;
            }
        }
        return null;
    }

    /**
     * Checks if a person with the specified ID exists in the collection.
     *
     * @param ID The ID of the person.
     * @return true if a person with the specified ID exists, false otherwise.
     */
    public static boolean idExistence(int ID) {
        for (Person person : personTreeSet) {
            if (person.getId() == ID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints information about the collection, such as its type, initialization date, and number of elements.
     */
    public static void information() {
        ConsolePrinter.printInformation("Type of collection: " + personTreeSet.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + personTreeSet.size() + ".");
    }

    /**
     * Prints detailed information about all persons in the collection.
     * Throws an EmptyCollectionException if the collection is empty.
     */
    public static void fullInformation() {
        try {
            if (personTreeSet.isEmpty()) throw new EmptyCollectionException();
            for (Person person : personTreeSet) {
                ConsolePrinter.printInformation(person);
            }
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("The collection is empty.");
        }
    }

    /**
     * Adds a person to the collection.
     *
     * @param person The person to be added.
     */
    public static void addPerson(Person person) {
        personTreeSet.add(person);
    }

    /**
     * Adds a person to the collection if it has the maximum value based on the natural ordering.
     * If the collection is empty, the person is added automatically.
     *
     * @param person The person to be added.
     */
    public static void addIfMax(Person person) {
        if (personTreeSet.isEmpty()) {
            addPerson(person);
        } else {
            Person maxPerson = personTreeSet.last();
            if (person.compareTo(maxPerson) > 0) {
                addPerson(person);
            } else {
                ConsolePrinter.printError("Person not added.");
            }
        }
    }

    public static void addIfMin(Person person) {
        if (personTreeSet.isEmpty()) {
            addPerson(person);
        } else {
            Person minPerson = personTreeSet.first();
            if (person.compareTo(minPerson) < 0) {
                addPerson(person);
            } else {
                ConsolePrinter.printError("Person not added.");
            }
        }
    }

    /**
     * Updates an element in the collection with the specified ID.
     * The attributes of the person are updated with the attributes of the newPerson.
     *
     * @param newPerson The updated person.
     * @param ID        The ID of the person to be updated.
     */
    public static void updateElement(Person newPerson, Integer ID) {
        Person personToUpdate = getPersonByID(ID);
        if (personToUpdate != null) {
            personTreeSet.remove(personToUpdate); // Xóa person cũ
            personToUpdate.setName(newPerson.getName());
            personToUpdate.setCoordinates(newPerson.getCoordinates());
            personToUpdate.setHeight(newPerson.getHeight());
            personToUpdate.setBirthday(newPerson.getBirthday());
            personToUpdate.setEyeColor(newPerson.getEyeColor());
            personToUpdate.setHairColor(newPerson.getHairColor());
            personToUpdate.setLocation(newPerson.getLocation());
            personTreeSet.add(personToUpdate);
        }
    }

    /**
     * Clears the collection by removing all persons.
     * The removed persons are stored in the clearList.
     */
    public static void clearCollection() {
        clearList.addAll(personTreeSet);
        personTreeSet.clear();
    }

    /**
     * Removes a person from the collection based on its ID.
     *
     * @param ID The ID of the person to be removed.
     */
    public static void removeElement(Integer ID) {
        Person personToRemove = getPersonByID(ID);
        if (personToRemove != null) {
            personTreeSet.remove(personToRemove);
        }
    }

    /**
     * Prints the last 11 executed commands (without arguments).
     */
    public static void printLast11Commands() {
        if (historyCommandList.isEmpty()) {
            ConsolePrinter.printInformation("Command history is empty.");
            return;
        }
        int count = Math.min(11,historyCommandList.size());
        List<String> lastCommands = new ArrayList<>(historyCommandList);
        ConsolePrinter.printInformation("Last " + count + " commands:");
        for (int i = lastCommands.size() - count; i < lastCommands.size(); i++) {
            ConsolePrinter.printInformation(lastCommands.get(i));
        }
    }

    /**
     * Calculate and print the total value of the height field for all elements in the collection.
     */
    public static void sumOfHeight() {
        if (personTreeSet.isEmpty()) {
            ConsolePrinter.printInformation("Collection is empty. Sum of height is 0.");
            return;
        }
        int sum = 0;
        for (Person person : personTreeSet) {
            sum += person.getHeight();
        }
        ConsolePrinter.printInformation("Sum of height for all elements in the collection: " + sum);
    }

    /**
     * Calculates and prints the average value of the height field for all elements in the collection.
     */
    public static void averageOfHeight() {
        if (personTreeSet.isEmpty()) {
            ConsolePrinter.printInformation("Collection is empty. Average height is 0.");
            return;
        }
        int sum = 0;
        for (Person person : personTreeSet) {
            sum += person.getHeight();
        }
        double average = (double) sum / personTreeSet.size();

        ConsolePrinter.printInformation("Average height for all elements in the collection: " + average);
    }

    /**
     * Prints all elements of the collection in descending order based on the ID field.
     */
    public static void printDescending() {
        if (personTreeSet.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }
        ConsolePrinter.printInformation("Collection in descending order:");
        personTreeSet.descendingSet().forEach(System.out::println);
    }

    public static int idmax(){
        if (personTreeSet.isEmpty()){
            return 1;
        }
        Person maxPerson = personTreeSet.first();
        for (Person person :personTreeSet) {
            if (person.getId() > maxPerson.getId()) {
                maxPerson = person;
            }
        }
        return maxPerson.getId();
    }

    /**
     * Loads the collection from a file with the specified name.
     *
     * @param fileName The name of the file to load the collection from.
     */
    public static void getCollectionFromFile(String fileName) {
        personTreeSet = CSVProcess.loadCollection(fileName);
    }

    /**
     * Saves the collection to a file.
     */
    public static void saveCollectionToFile() {
        CSVProcess.writeCollection();
    }
}