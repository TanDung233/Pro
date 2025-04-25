package main.java.utility;

import main.java.data.Person;
import main.java.utility.csv.CSVProcess;
import java.time.ZonedDateTime;
import java.util.*;

import main.java.exceptions.EmptyCollectionException;

public class CollectionManager {
    private Stack<String> historyCommandList = new Stack<>();
    private List<Person> clearList = new ArrayList<>();
    private TreeSet<Person> personTreeSet;
    private ZonedDateTime initializationDate;
    private ConsolePrinter consolePrinter;
    private CSVProcess csvprocess;


    public CollectionManager(ConsolePrinter consolePrinter, CSVProcess csvprocess ) {
        this.consolePrinter = consolePrinter;
        this.personTreeSet = new TreeSet<>();
        this.initializationDate = ZonedDateTime.now();
        this.csvprocess = csvprocess;
        initializationCollection();
    }

    public Stack<String> getHistoryCommandList() {
        return historyCommandList;
    }

    public TreeSet<Person> getCollection() {
        return personTreeSet;
    }

    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    public void initializationCollection() {
        personTreeSet = new TreeSet<>();
        initializationDate = ZonedDateTime.now();
    }

    public Person getPersonByID(int ID) {
        for (Person person : personTreeSet) {
            if (person.getId() == ID) {
                return person;
            }
        }
        return null;
    }

    public boolean idExistence(int ID) {
        for (Person person : personTreeSet) {
            if (person.getId() == ID) {
                return true;
            }
        }
        return false;
    }

    public void information() {
        consolePrinter.printInformation("Type of collection: " + personTreeSet.getClass().getName() +
                ".\nInitialization Date: " + initializationDate +
                ".\nNumber of elements: " + personTreeSet.size() + ".");
    }

    public void fullInformation() {
        try {
            if (personTreeSet.isEmpty()) throw new EmptyCollectionException();
            for (Person person : personTreeSet) {
                consolePrinter.printInformation(person.toString());
            }
        } catch (EmptyCollectionException exception) {
            consolePrinter.printError("The collection is empty.");
        }
    }

    public void addPerson(Person person) {
        personTreeSet.add(person);
    }

    public void addIfMax(Person person) {
        if (personTreeSet.isEmpty()) {
            addPerson(person);
        } else {
            Person maxPerson = personTreeSet.last();
            if (person.compareTo(maxPerson) > 0) {
                addPerson(person);
            } else {
                consolePrinter.printError("Person not added.");
            }
        }
    }

    public void addIfMin(Person person) {
        if (personTreeSet.isEmpty()) {
            addPerson(person);
        } else {
            Person minPerson = personTreeSet.first();
            if (person.compareTo(minPerson) < 0) {
                addPerson(person);
            } else {
                consolePrinter.printError("Person not added.");
            }
        }
    }

    public void updateElement(Person newPerson, Integer ID) {
        Person personToUpdate = getPersonByID(ID);
        if (personToUpdate != null) {
            personTreeSet.remove(personToUpdate);
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

    public void clearCollection() {
        clearList.addAll(personTreeSet);
        personTreeSet.clear();
    }

    public void removeElement(Integer ID) {
        for (Person person : personTreeSet) {
            if (person.getId().equals(ID)) {
                personTreeSet.remove(person);
                break;
            }
        }
    }

    public void printLast11Commands() {
        if (historyCommandList.isEmpty()) {
            consolePrinter.printInformation("Command history is empty.");
            return;
        }
        int count = Math.min(11, historyCommandList.size());
        List<String> lastCommands = new ArrayList<>(historyCommandList);
        consolePrinter.printInformation("Last " + count + " commands:");
        for (int i = lastCommands.size() - count; i < lastCommands.size(); i++) {
            consolePrinter.printInformation(lastCommands.get(i));
        }
    }

    public void sumOfHeight() {
        if (personTreeSet.isEmpty()) {
            consolePrinter.printInformation("Collection is empty. Sum of height is 0.");
            return;
        }
        int sum = 0;
        for (Person person : personTreeSet) {
            sum += person.getHeight();
        }
        consolePrinter.printInformation("Sum of height for all elements in the collection: " + sum);
    }

    public void averageOfHeight() {
        if (personTreeSet.isEmpty()) {
            consolePrinter.printInformation("Collection is empty. Average height is 0.");
            return;
        }
        int sum = 0;
        for (Person person : personTreeSet) {
            sum += person.getHeight();
        }
        double average = (double) sum / personTreeSet.size();
        consolePrinter.printInformation("Average height for all elements in the collection: " + average);
    }

    public void printDescending() {
        if (personTreeSet.isEmpty()) {
            consolePrinter.printInformation("The collection is empty.");
            return;
        }
        consolePrinter.printInformation("Collection in descending order:");
        personTreeSet.descendingSet().forEach(System.out::println);
    }

    public int idmax() {
        if (personTreeSet.isEmpty()) {
            return 1;
        }
        Person maxPerson = personTreeSet.first();
        for (Person person : personTreeSet) {
            if (person.getId() > maxPerson.getId()) {
                maxPerson = person;
            }
        }
        return maxPerson.getId();
    }

    public void getCollectionFromFile(String fileName) {
        this.personTreeSet = this.csvprocess.loadCollection(fileName);
    }

    public void saveCollectionToFile() {
        this.csvprocess.writeCollection(personTreeSet);
    }
}