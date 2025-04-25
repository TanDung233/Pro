package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.ConsolePrinter;

/**
 * The ExitCommand class represents a command to end the program without saving to a file.
 */
public class ExitCommand extends AbstractCommand {
    private final ConsolePrinter consolePrinter;

    public ExitCommand(ConsolePrinter consolePrinter) {
        super("exit", "end the program (without saving to a file).");
        this.consolePrinter = consolePrinter;
    }

    private void exitProgram() {
        consolePrinter.printResult("The 'exit' command has been executed successfully!");
        consolePrinter.printResult("Program has been closed!");
        System.exit(0);
    }

    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        exitProgram();
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}