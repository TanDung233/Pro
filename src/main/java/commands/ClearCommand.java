package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The ClearCommand class represents a command to clear the collection.
 * It extends the AbstractCommand class.
 */
public class ClearCommand extends AbstractCommand {
    public ClearCommand() {
        super("clear", "clear collection");
    }

    public void clear() {
        CollectionManager.historyCommandList.push("clear");
        CollectionManager.clearCollection();
        ConsolePrinter.printResult("The 'clear' command has been executed successfully!");
        ConsolePrinter.printResult("The collection is cleared!");
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
        clear();
    }

    /**
     * Retrieves information about the clear command.
     */
    @Override
    public void getCommandInformation() { ConsolePrinter.printInformation(super.toString());
    }
}
