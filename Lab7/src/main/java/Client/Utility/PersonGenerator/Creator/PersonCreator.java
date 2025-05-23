package Client.Utility.PersonGenerator.Creator;

import Common.Data.Person.Color;
import Common.Data.Person.Coordinates;
import Common.Data.Person.Location;
import Common.Data.Person.Person;
import Common.Data.User;
import Common.Exception.WrongInputInScriptException;
import Client.Utility.ConsolePrinter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Creates Person objects based on user input.
 */
public class PersonCreator {
    private final PersonBuilder personBuilder;
    ConsolePrinter consolePrinter = new ConsolePrinter();
    public PersonCreator( PersonBuilder personBuilder) {
        this.personBuilder = personBuilder;
    }

    /**
     * Creates a Person object from user input
     * @return Created Person object
     */
    public Person createPerson(){
        Scanner scanner = new Scanner(System.in);
        Color eyeColor = null;
        String name = null;
        Double x = null;
        Double y = null;
        LocalDateTime birthday = null;
        int height = 0;
        Color hairColor = null;
        Double z = null;
        String locationName = null;
        User user = null;
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
        return new Person(0,name, new Coordinates(x, y), height, birthday,
                eyeColor, hairColor, new Location(x, y, z, locationName), user);
    }


    private Person buildPerson(String name, Double x, Double y, Double z,
                               int height, LocalDateTime birthday,
                               Color eyeColor, Color hairColor,
                               String locationName) {
        return new Person(
                0,
                name,
                new Coordinates(x, y),
                height,
                birthday,
                eyeColor,
                hairColor,
                new Location(x, y, z, locationName),
                null
        );
    }
}