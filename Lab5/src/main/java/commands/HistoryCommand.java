package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The HistoryCommand class represents a command to display the last 11 executed commands.
 * It extends the AbstractCommand class.
 */
public class HistoryCommand extends AbstractCommand {
    public HistoryCommand() {
        super("history", "display the last 11 executed commands (without arguments)");
    }

    public void history() {
        CollectionManager.printLast11Commands();
        ConsolePrinter.printResult("The 'history' command has been executed successfully!");
        CollectionManager.historyCommandList.push("history");
    }

    /**
     * Executes the history command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        history();
    }

    /**
     * Retrieves information about the history command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}