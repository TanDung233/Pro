package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Memory.CollectionManager;

/**
 * The command for reading information from this collection
 */
public class InfoCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "print information about the collection to standard output (type, initialization date, number of elements, etc.)");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request){
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            String message = collectionManager.information();
            return new Response(message+ "\nThe 'info' command has been executed successfully!");
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
