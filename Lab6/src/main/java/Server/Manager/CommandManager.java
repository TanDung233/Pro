package Server.Manager;

import Server.Commands.*;
import org.checkerframework.checker.units.qual.C;

public class CommandManager extends StandardCommandManager {
    private CollectionManager collectionManager;

    public CommandManager(CollectionManager collectionManager) {
        super(13);
        this.collectionManager = collectionManager;
        register();
    }

    /**
     * Initialize all commands
     */
    public void register() {
        commands.add( new AddCommand(collectionManager) );
        commands.add( new AddIfMaxCommand(collectionManager) );
        commands.add( new AddIfMinCommand(collectionManager) );
        commands.add( new AverageCommand(collectionManager) );
        commands.add( new ClearCommand(collectionManager) );
        commands.add( new ExecuteScriptCommand(collectionManager) );
        commands.add( new ExitCommand() );
        commands.add( new HelpCommand(this) );
        commands.add( new HistoryCommand(collectionManager));
        commands.add( new InfoCommand(collectionManager) );
        commands.add( new PrintdescendingCommand(collectionManager) );
        commands.add( new RemoveByIdCommand(collectionManager) );
        commands.add( new ShowCommand(collectionManager) );
        commands.add( new SumCommand(collectionManager) );
        commands.add( new UpdateCommand(collectionManager) );
        commands.add(new NoSuchCommand());
    }
}