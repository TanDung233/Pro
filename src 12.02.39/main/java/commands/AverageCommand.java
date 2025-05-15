package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The AverageCommand class calculates and displays the average height value
 * for all elements in the collection.
 */
public class AverageCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public AverageCommand(CollectionManager collectionManager,
                          ConsolePrinter consolePrinter) {
        super("average_of_height",
                "calculate and display the average height value for all elements in the collection");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void calculateAverage() {
        collectionManager.averageOfHeight();
        collectionManager.getHistoryCommandList().push("average_of_height");
        consolePrinter.printResult("The 'average_of_height' command has been executed successfully!");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        calculateAverage();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}