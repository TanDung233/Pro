package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.mode.FileScriptMode;

/**
 * The ExecuteScriptCommand class represents a command to read and execute the script from the specified file.
 * It extends the AbstractCommand class.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private static String path;

    public ExecuteScriptCommand() {
        super("execute_script file_name", "read and execute the script from the specified file.");
    }

    public void executeScript(String path) {

        FileScriptMode fileScriptMode = new FileScriptMode(path);
        fileScriptMode.executeMode();
        ConsolePrinter.printResult("The 'execute_script' command has been executed successfully!");
        CollectionManager.historyCommandList.push("execute_script");
    }

    /**
     * Executes the execute_script command.
     *
     * @param arg the arguments for the command
     * @throws StackOverflowError            if a stack overflow occurs
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws StackOverflowError, WrongAmountOfElementsException {
        try {
            if (arg.length != 2) throw new WrongAmountOfElementsException();
            ExecuteScriptCommand.path = arg[1];
            executeScript(path);
        } catch (StackOverflowError error) {
            ConsolePrinter.printError("Stack overflow occurred");
        }
    }

    /**
     * Retrieves information about the execute_script command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
