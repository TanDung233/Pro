package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The HistoryCommand class displays the last 11 executed commands.
 */
public class HistoryCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public HistoryCommand(CollectionManager collectionManager,
                          ConsolePrinter consolePrinter) {
        super("history", "display the last 11 executed commands (without arguments)");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void displayCommandHistory() {
        collectionManager.printLast11Commands();
        consolePrinter.printResult("The 'history' command has been executed successfully!");
        collectionManager.getHistoryCommandList().push("history");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        displayCommandHistory();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}