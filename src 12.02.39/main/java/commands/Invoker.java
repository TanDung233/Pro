package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.ConsolePrinter;

import java.util.HashMap;
import java.util.Map;

/**
 * Executes and manages registered commands.
 */
public class Invoker {
    private final Map<String, AbstractCommand> commands;
    private final ConsolePrinter consolePrinter;

    public Invoker(ConsolePrinter consolePrinter) {
        this.commands = new HashMap<>();
        this.consolePrinter = consolePrinter;
    }

    /**
     * Registers a command with its name.
     * @param name The command name
     * @param command The command instance
     */
    public void register(String name, AbstractCommand command) {
        commands.put(name, command);
    }

    /**
     * Executes the specified command.
     * @param input The command input array
     * @return true if execution was successful, false otherwise
     */
    public boolean execute(String[] input) {
        if (input == null || input.length == 0 || input[0].trim().isEmpty()) {
            return false;
        }

        try {
            if (input.length > 0) {
                AbstractCommand command = commands.get(input[0]);
                command.execute(input);
                return true;
            }
        } catch (WrongAmountOfElementsException e) {
            String message = e.getMessage();
            consolePrinter.printError("Invalid command arguments: " + (message != null ? message : "(no additional info)"));
        } catch (Exception e) {
            consolePrinter.printError("Error executing command: " + e.getMessage());
        }

        return false;
    }


    /**
     * Gets all registered commands.
     * @return Map of command names to command instances
     */
    public Map<String, AbstractCommand> getCommands() {
        return new HashMap<>(commands);
    }
}