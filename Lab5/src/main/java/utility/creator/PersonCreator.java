package main.java.utility.creator;

import main.java.data.*;
import main.java.exceptions.WrongInputInScriptException;
import main.java.utility.ConsolePrinter;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * The PersonCreator class is responsible for creating Person objects based on user input.
 */
public class PersonCreator {
    private static String name;
    private static Integer x;
    private static Double y;
    private static Double z;
    private static int height;
    private static LocalDateTime birthday;
    private static Color eyeColor;
    private static Color hairColor;
    private static String locationName;

    /**
     * Creates a Person object based on user input.
     *
     * @param scanner the Scanner object used to read user input.
     * @return the created Person object.
     */
    public static Person personCreator(Scanner scanner) {
        try {
            PersonBuilder personBuilder = new PersonBuilder(scanner);
            name = personBuilder.nameAsker();
            x = personBuilder.xAsker();
            y = personBuilder.yAsker();
            z = personBuilder.zAsker();
            height = personBuilder.heightAsker();
            birthday = personBuilder.birthdayAsker();
            eyeColor = personBuilder.eyeColorAsker();
            hairColor = personBuilder.hairColorAsker();
            locationName = personBuilder.locationNameAsker();
        } catch (WrongInputInScriptException exception) {
            ConsolePrinter.printError("Wrong input! Please check your data and try again.");
        }
        return new Person(IDGenerator.generateID(),name, new Coordinates(x, y), height, birthday,
                    eyeColor, hairColor, new Location(x, y, z, locationName));

    }

}