package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

public class AverageCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public AverageCommand(CollectionManager collectionManager,
                          ConsolePrinter consolePrinter) {
        super("average_of_height", "calculate and display the average height value");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    public void average() {
        collectionManager.averageOfHeight();
        collectionManager.getHistoryCommandList().push("average_of_height");
        consolePrinter.printResult("Command executed successfully!");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        average();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}