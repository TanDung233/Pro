package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The HelpCommand class represents a command that provides help information.
 * This command is typically used to display information about how to use the application
 * or to provide details about other available commands.
 */
public class HelpCommand extends AbstractCommand {
    Invoker invoker = new Invoker();
    public HelpCommand() {
        super("help", "display help on available commands");
    }

    public void help() {
        invoker.getCommands().forEach((name, command) -> command.getCommandInformation());
        ConsolePrinter.printResult("The 'help' command has been executed successfully!");
        CollectionManager.historyCommandList.push("help");
    }
    /**
     * Executes the help command, displaying information about how to use the application
     * or providing details about other available commands.
     *
     * @param arg The command argument (not used for this command).
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 1) throw new WrongAmountOfElementsException();
        help();
    }

    /**
     * Retrieves information about the help command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
