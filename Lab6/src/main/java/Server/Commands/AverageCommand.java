package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.CollectionManager;

/**
 * The AverageCommand class calculates and displays the average height value
 * for all elements in the collection.
 */
public class AverageCommand extends Commands {
    private final CollectionManager collectionManager;

    public AverageCommand(CollectionManager collectionManager) {
        super("average_of_height",
                "calculate and display the average height value for all elements in the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            String messgae = collectionManager.averageOfHeight();
            collectionManager.getHistoryCommandList().push("average_of_height");
            return new Response(messgae + "\nThe 'average' command has been executed successfully!");
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}