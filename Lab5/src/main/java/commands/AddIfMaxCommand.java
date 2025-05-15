package main.java.commands;


import main.java.data.Person;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonCreator;

import java.util.Scanner;

/**
 * The AddIfMaxCommand class represents a command to add a new element to a collection
 * if its value is greater than the value of the largest element of this collection.
 * It extends the AbstractCommand class.
 */
public class AddIfMaxCommand extends AbstractCommand {
    public AddIfMaxCommand() {
        super("add_if_max {element}", "add a new element to a collection if its value is greater than the value of the largest element of this collection");
    }

    public static void addIfMax(Scanner scanner) {
        Person person = PersonCreator.personCreator(scanner);
        CollectionManager.addIfMax(person);
        CollectionManager.historyCommandList.push("add_if_max");
        ConsolePrinter.printResult("The 'add_if_max' command has been executed successfully!");
    }
    /**
     * Executes the add_if_max command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        addIfMax(new Scanner(System.in));
    }

    /**
     * Retrieves information about the add_if_max command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
