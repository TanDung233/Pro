package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Memory.CollectionManager;

/**
 * Calculates and displays the sum of height values for all elements in the collection.
 */
public class SumCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public SumCommand(CollectionManager collectionManager){
    super("sum_of_height",
                "calculate and display the sum of height values for all elements in the collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            String message = collectionManager.sumOfHeight();
            return new Response(message + "\nThe 'sum_of_height' command has been executed successfully!");
        } catch (CommandSyntaxIsWrongException e) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
