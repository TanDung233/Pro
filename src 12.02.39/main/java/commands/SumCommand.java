package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * Calculates and displays the sum of height values for all elements in the collection.
 */
public class SumCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public SumCommand(CollectionManager collectionManager,
                      ConsolePrinter consolePrinter) {
        super("sum_of_height",
                "calculate and display the sum of height values for all elements in the collection");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void calculateAndDisplayHeightSum() {
        collectionManager.sumOfHeight();
        collectionManager.getHistoryCommandList().push("sum_of_height");
        consolePrinter.printResult("The 'sum_of_height' command has been executed successfully!");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        calculateAndDisplayHeightSum();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}