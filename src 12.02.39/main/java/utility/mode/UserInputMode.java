package main.java.utility.mode;

import main.java.commands.Invoker;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.exceptions.WrongInputInScriptException;
import main.java.utility.ConsolePrinter;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The UserInputMode class represents the interactive mode for receiving user input and executing commands.
 */
public class UserInputMode implements IMode {
    private Scanner userScanner;
    private final Invoker invoker;
    private final ConsolePrinter consolePrinter;

    /**
     * Constructor for UserInputMode
     * @param invoker The command invoker
     * @param consolePrinter The console output handler
     */
    public UserInputMode(Invoker invoker, ConsolePrinter consolePrinter) {
        this.userScanner = new Scanner(System.in);
        this.invoker = invoker;
        this.consolePrinter = consolePrinter;
    }

    /**
     * Executes the interactive mode for receiving user input and executing commands.
     */
    @Override
    public void executeMode() {
        boolean commandStatus;
        try {
            userScanner = new Scanner(System.in);
            do{
                commandStatus = invoker.execute(userScanner.nextLine().trim().toLowerCase().split(" "));
            } while (userScanner.hasNextLine());
        } catch (NoSuchElementException exception) {
            consolePrinter.printError("No user input detected!");
        } catch (IllegalStateException exception) {
            consolePrinter.printError("Unexpected error!");
        }
    }

    /**
     * Retrieves the scanner used for reading user input in the interactive mode.
     *
     * @return The scanner used for reading user input.
     */
    @Override
    public  Scanner getScanner() {
        return userScanner;
    }

}
