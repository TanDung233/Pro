package main.java.commands;

import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonBuilder;
import main.java.utility.creator.PersonCreator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages command registration, initialization and execution.
 */
public class CommandManager {
    private final Map<String, AbstractCommand> commands = new LinkedHashMap<>();
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;
    private final PersonCreator personCreator;
    private final PersonBuilder personBuilder;
    private final Invoker invoker;

    public CommandManager(CollectionManager collectionManager,
                          ConsolePrinter consolePrinter,
                          PersonCreator personCreator,
                          PersonBuilder personBuilder,
                          Invoker invoker) {
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
        this.personCreator = personCreator;
        this.personBuilder = personBuilder;
        this.invoker = invoker;
        this.load();
    }

    /**
     * Loads all commands into the command map.
     */
    public void load() {
        this.loadCommand("help", new HelpCommand(invoker, collectionManager, consolePrinter));
        this.loadCommand("info", new InfoCommand(collectionManager, consolePrinter));
        this.loadCommand("show", new ShowCommand(collectionManager, consolePrinter));
        this.loadCommand("add", new AddCommand(collectionManager, personCreator, consolePrinter));
        this.loadCommand("update", new UpdateCommand(collectionManager, consolePrinter, personCreator));
        this.loadCommand("remove_by_id", new RemoveByIdCommand(collectionManager, consolePrinter));
        this.loadCommand("clear", new ClearCommand(collectionManager, consolePrinter));
        this.loadCommand("add_if_max", new AddIfMaxCommand(collectionManager, personCreator, consolePrinter));
        this.loadCommand("add_if_min", new AddIfMinCommand(collectionManager, personCreator, consolePrinter));
        this.loadCommand("save", new SaveCommand(collectionManager, consolePrinter));
        this.loadCommand("execute_script", new ExecuteScriptCommand(collectionManager, consolePrinter, personBuilder, personCreator, invoker));
        this.loadCommand("sum_of_height", new SumCommand(collectionManager, consolePrinter));
        this.loadCommand("average_of_height", new AverageCommand(collectionManager, consolePrinter));
        this.loadCommand("print_descending", new PrintdescendingCommand(collectionManager, consolePrinter));
        this.loadCommand("history", new HistoryCommand(collectionManager, consolePrinter));
        this.loadCommand("exit", new ExitCommand(consolePrinter));

        consolePrinter.printInformation(String.format("Registered %d commands successfully", commands.size()));
    }

    /**
     * Registers a command in the command map.
     *
     * @param name    The command name.
     * @param command The command instance.
     */
    public void loadCommand(String name, AbstractCommand command) {
        commands.put(name, command);
        invoker.register(name, command);
    }

    /**
     * Executes a command based on the given request.
     *
     * @param commandName The name of the command to execute.
     * @param arguments   The arguments for the command.
     * @return The result of the command execution.
     */
    public String execute(String commandName, String[] arguments) {
        try {
            if (commands.containsKey(commandName)) {
                AbstractCommand command = commands.get(commandName);
                command.execute(arguments);
                return String.format("Command '%s' executed successfully.", commandName);
            } else {
                return String.format("Command '%s' not found.", commandName);
            }
        } catch (Exception e) {
            return "Error executing command: " + e.getMessage();
        }
    }

}