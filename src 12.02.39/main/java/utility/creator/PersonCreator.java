package main.java.utility.creator;

import main.java.data.*;
import main.java.exceptions.WrongInputInScriptException;
import main.java.utility.ConsolePrinter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Creates Person objects based on user input.
 */
public class PersonCreator {
    private final PersonBuilder personBuilder;
    private final IDGenerator idGenerator;
    private final ConsolePrinter consolePrinter;

    public PersonCreator(PersonBuilder personBuilder,
                         IDGenerator idGenerator,
                         ConsolePrinter consolePrinter) {
        this.personBuilder = personBuilder;
        this.idGenerator = idGenerator;
        this.consolePrinter = consolePrinter;
    }

    /**
     * Creates a Person object from user input
     * @param scanner Scanner for user input
     * @return Created Person object
     * @throws WrongInputInScriptException if invalid input is provided in script mode
     */
    public Person createPerson(Scanner scanner) throws WrongInputInScriptException {
        Color eyeColor = null;
        String name = null;
        Integer x = null;
        Double y = null;
        LocalDateTime birthday = null;
        int height = 0;
        Color hairColor = null;
        Double z = null;
        String locationName = null;
        try {
            name = personBuilder.nameAsker();
            x = personBuilder.xAsker();
            y = personBuilder.yAsker();
            z = personBuilder.zAsker();
            height = personBuilder.heightAsker();
            birthday = personBuilder.birthdayAsker();
            eyeColor = personBuilder.eyeColorAsker();
            hairColor = personBuilder.hairColorAsker();
            locationName = personBuilder.locationNameAsker();

            return buildPerson(name, x, y, z, height, birthday, eyeColor, hairColor, locationName);

        } catch (WrongInputInScriptException exception) {
            consolePrinter.printError("Wrong input! Please check your data and try again.");
        }
        return new Person(idGenerator.getNextId(), name, new Coordinates(x, y), height, birthday,
                eyeColor, hairColor, new Location(x, y, z, locationName));
    }

    /**
     * Creates a Person object with pre-validated values
     */
    public Person createPerson(String name, Integer x, Double y, Double z,
                               int height, LocalDateTime birthday,
                               Color eyeColor, Color hairColor,
                               String locationName) {
        return buildPerson(name, x, y, z, height, birthday, eyeColor, hairColor, locationName);
    }

    private Person buildPerson(String name, Integer x, Double y, Double z,
                               int height, LocalDateTime birthday,
                               Color eyeColor, Color hairColor,
                               String locationName) {
        return new Person(
                idGenerator.getNextId(),
                name,
                new Coordinates(x, y),
                height,
                birthday,
                eyeColor,
                hairColor,
                new Location(x, y, z, locationName)
        );
    }
}