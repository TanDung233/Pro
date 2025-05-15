package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The PrintDescendingCommand class represents a command to print all elements of the collection in descending order.
 */
public class PrintdescendingCommand extends AbstractCommand {
    public PrintdescendingCommand() {
        super("print_descending", "print all elements of the collection in descending order");
    }

    public void printdes() {
        CollectionManager.printDescending();
        CollectionManager.historyCommandList.push("print_descending");
        ConsolePrinter.printResult("The 'print_descending' command has been executed successfully!");
    }

    /**
     * Executes the print_descending command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        printdes();
    }

    /**
     * Retrieves information about the print_descending command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}