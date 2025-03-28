package main.java.commands;

import main.java.data.Person;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonCreator;

import java.util.Scanner;

/**
 * The AddCommand class represents a command to add a new element to the collection.
 * It extends the AbstractCommand class.
 */
public class AddCommand extends AbstractCommand {

    public AddCommand() {
        super("add {element}", "add a new element to the collection");
    }

    public static void add(Scanner scanner) {
        Person person = PersonCreator.personCreator(scanner);
        CollectionManager.addPerson(person);
        ConsolePrinter.printResult("The 'add' command has been executed successfully!");
        CollectionManager.historyCommandList.push("add");
    }
    /**
     * Executes the add command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        add(new Scanner(System.in));
    }

    /**
     * Retrieves information about the add command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
