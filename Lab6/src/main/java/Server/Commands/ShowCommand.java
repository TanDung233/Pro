package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.CollectionManager;

/**
 * Displays all elements of the collection.
 */
public class ShowCommand extends Commands {
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "print all elements of the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            collectionManager.getHistoryCommandList().push("show");
            return new Response(collectionManager.getCollection().toString());
        } catch (CommandSyntaxIsWrongException e) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
