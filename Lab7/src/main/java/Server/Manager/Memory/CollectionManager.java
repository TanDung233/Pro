package Server.Manager.Memory;

import Common.Data.Person.Person;
import Common.Data.User;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.ServerApp;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.concurrent.locks.ReentrantLock;

import Client.Utility.ConsolePrinter;


public class CollectionManager {
    private final ReentrantLock lock = new ReentrantLock();
    private TreeSet<Person> personTreeSet;
    private static ZonedDateTime lastInitTime;
    private ZonedDateTime saveTime;
    private DatabaseCollectionManager databaseCollectionManager;
    private ConsolePrinter consolePrinter;


    public CollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        this.databaseCollectionManager = databaseCollectionManager;
        loadCollection();
        saveTime = null;
        this.consolePrinter = new ConsolePrinter();
    }

    /**
     * Load collection's data from DB
     */
    private void loadCollection(){
        try{
            personTreeSet  = databaseCollectionManager.getCollection();
            lastInitTime = ZonedDateTime.now();
            ServerApp.logger.log(Level.INFO, "Collection's data was loaded from database." );
        } catch (Exception e){
            personTreeSet = new TreeSet<>();
            ServerApp.logger.log(Level.WARNING, e.toString());
        }
    }

    public TreeSet<Person> getCollection() {
        lock.lock();
        try {
            return personTreeSet;
        } finally {
            lock.unlock();
        }
    }


    public Person getPersonByID(int ID) {
        lock.lock();
        try {
            for (Person person : personTreeSet) {
                if (person.getId() == ID) {
                    return person;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public boolean idExistence(int ID) {
        lock.lock();
        try {
            for (Person person : personTreeSet) {
                if (person.getId() == ID) {
                    return true;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public String information() {
        lock.lock();
        try {
            return ("Type of collection: " + personTreeSet.getClass().getName() +
                    ".\nNumber of elements: " + personTreeSet.size() + ".");
        } finally {
            lock.unlock();
        }
    }

    public void addPerson(Person person, User user) {
        lock.lock();
        try {
            person.setUser(user);
            personTreeSet.add(person);
        } finally {
            lock.unlock();
        }
    }

    public void addIfMax(Person person,User user) {
        lock.lock();
        try {
            person.setUser(user);
            if (personTreeSet.isEmpty()) {
                addPerson(person,user);
            } else {
                Person maxPerson = personTreeSet.last();
                if (person.compareTo(maxPerson) > 0) {
                    addPerson(person,user);
                } else {
                    consolePrinter.printError("Person not added.");
                }
            }        } finally {
            lock.unlock();
        }
    }

    public void addIfMin(Person person,User user) {
        lock.lock();
        try {
            person.setUser(user);
            if (personTreeSet.isEmpty()) {
                addPerson(person,user);
            } else {
                Person minPerson = personTreeSet.first();
                if (person.compareTo(minPerson) < 0) {
                    addPerson(person,user);
                } else {
                    consolePrinter.printError("Person not added.");
                }
            }        } finally {
            lock.unlock();
        }
    }


    public void removeFromCollection(Person person){
        personTreeSet.remove(person.getId());
    }


    public void removeElement(Integer ID) {
        lock.lock();
        try {
            for (Person person : personTreeSet) {
                if (person.getId().equals(ID)) {
                    personTreeSet.remove(person);
                    break;
                }
            }        } finally {
            lock.unlock();
        }
    }


    public String sumOfHeight() {
        lock.lock();
        try {
            if (personTreeSet.isEmpty()) {
                return ("Collection is empty. Sum of height is 0.");
            }
            int sum = 0;
            for (Person person : personTreeSet) {
                sum += person.getHeight();
            }
            return ("Sum of height for all elements in the collection: " + sum);
        } finally {
            lock.unlock();
        }
    }

    public String averageOfHeight() {
        lock.lock();
        try {
            if (personTreeSet.isEmpty()) {
                return ("Collection is empty. Average height is 0.");
            }
            int sum = 0;
            for (Person person : personTreeSet) {
                sum += person.getHeight();
            }
            double average = (double) sum / personTreeSet.size();
            return ("Average height for all elements in the collection: " + average);
        } finally {
            lock.unlock();
        }
    }

    public String printDescending() {
        lock.lock();
        try {
            if (personTreeSet.isEmpty()) {
                return "The collection is empty.";
            }

            return personTreeSet.descendingSet().stream()
                    .map(Person::toString)
                    .collect(Collectors.joining("\n"));
        } finally {
            lock.unlock();
        }
    }

    /**
     * Save this collection into database
     * */
    public void saveCollection(){
        saveTime = ZonedDateTime.now();

    }

}