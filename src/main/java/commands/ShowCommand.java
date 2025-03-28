package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The ShowCommand class represents a command to print all elements of the collection.
 * This command prints all elements of the collection in string representation to the standard output.
 */
public class ShowCommand extends AbstractCommand {
    public ShowCommand() {
        super("show", "print all elements of a collection to standard output in string representation.");
    }

    public void show() {
        CollectionManager.fullInformation();
        ConsolePrinter.printResult("The 'show' command has been executed successfully!");
        CollectionManager.historyCommandList.push("show");
    }

    /**
     * Executes the show command, printing all elements of the collection to the standard output.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 1) throw new WrongAmountOfElementsException();
        show();
    }

    /**
     * Displays information about the show command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
