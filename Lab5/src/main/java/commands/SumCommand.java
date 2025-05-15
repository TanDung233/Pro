package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The SumOfHeightCommand class represents a command to calculate and display the sum of height values for all elements in the collection.
 */
public class SumCommand extends AbstractCommand {
    public SumCommand() {
        super("sum_of_height", "calculate and display the sum of height values for all elements in the collection");
    }

    public void sum() {
        CollectionManager.sumOfHeight();
        CollectionManager.historyCommandList.push("sum_of_height");
        ConsolePrinter.printResult("The 'sum_of_height' command has been executed successfully!");
    }

    /**
     * Executes the sum_of_height command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException(); // Không có đối số nào được chấp nhận
        sum();
    }

    /**
     * Retrieves information about the sum_of_height command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}