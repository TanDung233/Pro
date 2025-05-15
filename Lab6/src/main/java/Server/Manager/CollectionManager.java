package Server.Manager;

import Common.Data.Person;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import Server.Utility.ConsolePrinter;
import Server.Utility.Csv.CSVProcess;
import Common.Exception.EmptyCollectionException;

public class CollectionManager {
    private Stack<String> historyCommandList = new Stack<>();
    private List<Person> clearList = new ArrayList<>();
    private TreeSet<Person> personTreeSet;
    private ZonedDateTime initializationDate;
    private ConsolePrinter consolePrinter;
    private CSVProcess csvprocess;


    public CollectionManager( CSVProcess csvprocess ) {
        this.csvprocess = csvprocess;
        this.personTreeSet = new TreeSet<>();
        if (personTreeSet == null) System.exit(1);
        this.consolePrinter = new ConsolePrinter();
        this.initializationDate = ZonedDateTime.now();
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

    public String information() {
        return ("Type of collection: " + personTreeSet.getClass().getName() +
                ".\nInitialization Date: " + initializationDate +
                ".\nNumber of elements: " + personTreeSet.size() + ".");
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

    public String printLast11Commands() {
        if (historyCommandList.isEmpty()) {
            return ("Command history is empty.");
        }
        int count = Math.min(11, historyCommandList.size());
        List<String> lastCommands = new ArrayList<>(historyCommandList);
        return ("Last " + count + " commands: " + lastCommands.toString());
    }

    public String sumOfHeight() {
        if (personTreeSet.isEmpty()) {
            return ("Collection is empty. Sum of height is 0.");
        }
        int sum = 0;
        for (Person person : personTreeSet) {
            sum += person.getHeight();
        }
        return ("Sum of height for all elements in the collection: " + sum);
    }

    public String averageOfHeight() {
        if (personTreeSet.isEmpty()) {
            return ("Collection is empty. Average height is 0.");
        }
        int sum = 0;
        for (Person person : personTreeSet) {
            sum += person.getHeight();
        }
        double average = (double) sum / personTreeSet.size();
        return ("Average height for all elements in the collection: " + average);
    }

    public String printDescending() {
        if (personTreeSet.isEmpty()) {
            return "The collection is empty.";
        }

        return personTreeSet.descendingSet().stream()
                .map(Person::toString)
                .collect(Collectors.joining("\n"));
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