package main.java.commands;

import main.java.data.Person;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonCreator;

import java.util.Scanner;

public class AddCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;
    private final PersonCreator personCreator;

    public AddCommand(CollectionManager collectionManager,
                      ConsolePrinter consolePrinter,
                      PersonCreator personCreator) {
        super("add {element}", "add a new element to the collection");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
        this.personCreator = personCreator;
    }

    public void add(Scanner scanner) {
        Person person = personCreator.createPerson(scanner);
        collectionManager.addPerson(person);
        consolePrinter.printResult("The 'add' command has been executed successfully!");
        collectionManager.getHistoryCommandList().push("add");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        add(new Scanner(System.in));
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}