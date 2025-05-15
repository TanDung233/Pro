package Server.Manager;
import Server.Commands.*;
import java.util.*;

/**
 * Command Collection management: command collection + history (list of commands used)
 */

public abstract class StandardCommandManager {
    protected final Set<Commands> commands;
    private final ArrayDeque<String> commandHistory;
    private final int memoryCapicity;

    public StandardCommandManager() {
        commands = new HashSet<>();
        commandHistory = new ArrayDeque<>(100);
        this.memoryCapicity = 100;
    }

    public StandardCommandManager(int memoryCapicity) {
        commands = new HashSet<>();
        commandHistory = new ArrayDeque<>(memoryCapicity);
        this.memoryCapicity = memoryCapicity;
    }

    /**
     * Initialize all new command into set of commands
     */
    public abstract void register();

    /**
     * Get collection of commands
     * @return collection of commands
     */
    public Set<Commands> getCommands() {
        return commands;
    }

    /**
     * Get history of used command
     * @return history of used command
     */
    public ArrayDeque<String> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Record the command used in the history
     * @param command used command
     */
    public void addToHistory( String command ) {
        if ( commandHistory.size() == memoryCapicity ) {
            commandHistory.poll();
        }
        if (!command.equals("NoSuchCommand")) commandHistory.add(command);
    }

    /**
     * Get a command by his name
     * @param name name of command
     * @return command
     */
    public Commands getByName(String name){
        for (Commands c : commands) {
            if ( c.getName().equals(name)) return c;
        }
        return null;
    }
}