package main.java.commands;

import main.java.data.Person;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.exceptions.WrongInputInScriptException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonCreator;

import java.util.Scanner;

/**
 * The AddCommand class represents a command to add a new element to the collection.
 * It extends the AbstractCommand class.
 */
public class AddCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final PersonCreator personCreator;
    private final ConsolePrinter consolePrinter;

    public AddCommand(CollectionManager collectionManager,
                      PersonCreator personCreator,
                      ConsolePrinter consolePrinter) {
        super("add {element}", "add a new element to the collection");
        this.collectionManager = collectionManager;
        this.personCreator = personCreator;
        this.consolePrinter = consolePrinter;
    }

    private void add(Scanner scanner) throws WrongInputInScriptException {
        Person person = personCreator.createPerson(scanner);
        collectionManager.addPerson(person);
        consolePrinter.printResult("The 'add' command has been executed successfully!");
        collectionManager.getHistoryCommandList().push("add");
    }

    /**
     * Executes the add command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException, WrongInputInScriptException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        add(new Scanner(System.in));
    }

    /**
     * Retrieves information about the add command.
     */
    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}