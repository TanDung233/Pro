package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The ClearCommand class represents a command to clear the collection.
 * It extends the AbstractCommand class.
 */
public class ClearCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public ClearCommand(CollectionManager collectionManager,
                        ConsolePrinter consolePrinter) {
        super("clear", "clear collection");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void clearCollection() {
        collectionManager.getHistoryCommandList().push("clear");
        collectionManager.clearCollection();
        consolePrinter.printResult("The 'clear' command has been executed successfully!");
        consolePrinter.printResult("The collection is cleared!");
    }

    /**
     * Executes the clear command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        clearCollection();
    }

    /**
     * Retrieves information about the clear command.
     */
    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}