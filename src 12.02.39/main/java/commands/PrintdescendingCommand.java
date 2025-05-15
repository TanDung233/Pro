package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * Prints all elements of the collection in descending order.
 */
public class PrintdescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public PrintdescendingCommand(CollectionManager collectionManager,
                                  ConsolePrinter consolePrinter) {
        super("print_descending", "print all elements of the collection in descending order");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void printDescendingOrder() {
        collectionManager.printDescending();
        collectionManager.getHistoryCommandList().push("print_descending");
        consolePrinter.printResult("Elements printed in descending order successfully!");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        printDescendingOrder();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}