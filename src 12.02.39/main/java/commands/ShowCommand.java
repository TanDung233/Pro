package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * Displays all elements of the collection.
 */
public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public ShowCommand(CollectionManager collectionManager,
                       ConsolePrinter consolePrinter) {
        super("show", "print all elements of the collection");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void displayCollection() {
        collectionManager.fullInformation();
        collectionManager.getHistoryCommandList().push("show");
        consolePrinter.printResult("Collection displayed successfully!");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        displayCollection();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}