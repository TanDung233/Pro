package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.ConsolePrinter;

/**
 * The ExitCommand class represents a command to end the program without saving to a file.
 * It extends the AbstractCommand class.
 */
public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", "end the program (without saving to a file).");
    }

    public void exit() {
        ConsolePrinter.printResult("The 'exit' command has been executed successfully!");
        ConsolePrinter.printResult("Program has been closed!");
        System.exit(0);
    }
    /**
     * Executes the exit command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        exit();
    }

    /**
     * Retrieves information about the exit command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
