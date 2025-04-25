package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.exceptions.WrongInputInScriptException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonBuilder;
import main.java.utility.creator.PersonCreator;
import main.java.utility.mode.FileScriptMode;
import main.java.commands.Invoker;

/**
 * The ExecuteScriptCommand class represents a command to read and execute the script from the specified file.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;
    private final PersonBuilder personBuilder;
    private final PersonCreator personCreator;
    private final Invoker invoker;

    public ExecuteScriptCommand(CollectionManager collectionManager,
                                ConsolePrinter consolePrinter,
                                PersonBuilder personBuilder,
                                PersonCreator personCreator,
                                Invoker invoker) {
        super("execute_script file_name", "read and execute the script from the specified file.");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
        this.personBuilder = personBuilder;
        this.personCreator = personCreator;
        this.invoker = invoker;
    }

    private void executeScript(String path) throws WrongInputInScriptException {
        try {
            FileScriptMode fileScriptMode = new FileScriptMode(
                    path,
                    consolePrinter,
                    collectionManager,
                    personBuilder,
                    personCreator,
                    invoker
            );
            fileScriptMode.executeMode();
            consolePrinter.printResult("Script executed successfully: " + path);
            collectionManager.getHistoryCommandList().push("execute_script");
        } catch (StackOverflowError error) {
            consolePrinter.printError("Recursive script execution detected: " + path);
        }
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException, WrongInputInScriptException {
        if (arg.length != 2) {
            throw new WrongAmountOfElementsException();
        }
        executeScript(arg[1]);
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}