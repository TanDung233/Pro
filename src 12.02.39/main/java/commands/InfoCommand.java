package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The InfoCommand displays information about the collection.
 */
public class InfoCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public InfoCommand(CollectionManager collectionManager,
                       ConsolePrinter consolePrinter) {
        super("info", "print information about the collection (type, initialization date, number of elements, etc.)");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void displayCollectionInfo() {
        collectionManager.information();
        consolePrinter.printResult("The 'info' command has been executed successfully!");
        collectionManager.getHistoryCommandList().push("info");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 1) throw new WrongAmountOfElementsException();
        displayCollectionInfo();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}