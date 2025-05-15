package main.java.commands;

import main.java.data.Person;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.exceptions.WrongInputInScriptException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonCreator;

import java.util.Scanner;

/**
 * The AddIfMinCommand class represents a command to add a new element to a collection
 * if its value is less than the value of the smallest element of this collection.
 */
public class AddIfMinCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final PersonCreator personCreator;
    private final ConsolePrinter consolePrinter;

    public AddIfMinCommand(CollectionManager collectionManager,
                           PersonCreator personCreator,
                           ConsolePrinter consolePrinter) {
        super("add_if_min {element}",
                "add a new element to a collection if its value is less than the value of the smallest element of this collection");
        this.collectionManager = collectionManager;
        this.personCreator = personCreator;
        this.consolePrinter = consolePrinter;
    }

    private void addIfMin(Scanner scanner) throws WrongInputInScriptException {
        Person person = personCreator.createPerson(scanner);
        collectionManager.addIfMin(person);
        collectionManager.getHistoryCommandList().push("add_if_min");
        consolePrinter.printResult("The 'add_if_min' command has been executed successfully!");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException, WrongInputInScriptException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        addIfMin(new Scanner(System.in));
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}