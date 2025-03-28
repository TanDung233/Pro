package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The AverageOfHeightCommand class represents a command to calculate and display the average height value for all elements in the collection.
 */
public class AverageCommand extends AbstractCommand {
    public AverageCommand() {
        super("average_of_height", "calculate and display the average height value for all elements in the collection");
    }

    public void average() {
        CollectionManager.averageOfHeight();
        CollectionManager.historyCommandList.push("average_of_height");
        ConsolePrinter.printResult("The 'average_of_height' command has been executed successfully!");
    }

    /**
     * Executes the average_of_height command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        average();
    }

    /**
     * Retrieves information about the average_of_height command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}