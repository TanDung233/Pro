package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * Saves the collection to a file.
 */
public class SaveCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public SaveCommand(CollectionManager collectionManager,
                       ConsolePrinter consolePrinter) {
        super("save", "save the collection to the file");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void saveCollection() {
        try {
            collectionManager.saveCollectionToFile();
            collectionManager.getHistoryCommandList().push("save");
            consolePrinter.printResult("Collection saved successfully!");
        } catch (Exception e) {
            consolePrinter.printError("Failed to save collection: " + e.getMessage());
        }
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        saveCollection();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}