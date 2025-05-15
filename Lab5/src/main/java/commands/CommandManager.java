package main.java.commands;

public class CommandManager {

    /**
     * Starts the command execution process by registering commands with the invoker.
     */
    public static void commandStarter() {
        Invoker.register("help", new HelpCommand());
        Invoker.register("info", new InfoCommand());
        Invoker.register("show", new ShowCommand());
        Invoker.register("add", new AddCommand());
        Invoker.register("update", new UpdateCommand());
        Invoker.register("remove_by_id", new RemoveByIdCommand());
        Invoker.register("clear", new ClearCommand());
        Invoker.register("save", new SaveCommand());
        Invoker.register("execute_script", new ExecuteScriptCommand());
        Invoker.register("exit", new ExitCommand());
        Invoker.register("add_if_max", new AddIfMaxCommand());
        Invoker.register("add_if_min", new AddIfMinCommand());
        Invoker.register("history", new HistoryCommand());
        Invoker.register("sum_of_height", new SumCommand());
        Invoker.register("average_of_height", new AverageCommand());
        Invoker.register("print_descending", new PrintdescendingCommand());
    }
}
