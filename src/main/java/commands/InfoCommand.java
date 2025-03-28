package main.java.commands;

import main.java.utility.CollectionManager;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.ConsolePrinter;

/**
 * The InfoCommand class represents a command to print information about the collection.
 * It extends the AbstractCommand class.
 */
public class InfoCommand extends AbstractCommand {

    public InfoCommand() {
        super("info", "print information about the collection (type, initialization date, number of elements, etc.) to the standard output stream.");
    }

    public void info() {
        CollectionManager.information();
        ConsolePrinter.printResult("The 'info' command has been executed successfully!");
        CollectionManager.historyCommandList.push("info");
    }

    /**
     * Executes the info command, printing information about the collection.
     *
     * @param arg The command argument (not used for this command).
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 1) throw new WrongAmountOfElementsException();
        info();
    }

    /**
     * Retrieves information about the info command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
