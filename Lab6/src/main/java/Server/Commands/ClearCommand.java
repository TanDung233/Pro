package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Exception.WrongAmountOfElementsException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.CollectionManager;

/**
 * The ClearCommand class represents a command to clear the collection.
 * It extends the AbstractCommand class.
 */
public class ClearCommand extends Commands {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "clear collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            collectionManager.getHistoryCommandList().push("clear");
            collectionManager.clearCollection();
            return new Response("The 'clear' command has been executed successfully!\nCollection cleared!");
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}