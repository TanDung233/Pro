package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The HelpCommand class displays information about available commands.
 */
public class HelpCommand extends AbstractCommand {
    private final Invoker invoker;
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public HelpCommand(Invoker invoker,
                       CollectionManager collectionManager,
                       ConsolePrinter consolePrinter) {
        super("help", "display help on available commands");
        this.invoker = invoker;
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    private void displayHelp() {
        invoker.getCommands().values().forEach(command ->
                command.getCommandInformation()
        );
        consolePrinter.printResult("The 'help' command has been executed successfully!");
        collectionManager.getHistoryCommandList().push("help");
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 1) throw new WrongAmountOfElementsException();
        displayHelp();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}