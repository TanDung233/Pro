package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Memory.CollectionManager;

/**
 * Prints all elements of the collection in descending order.
 */
public class PrintdescendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public PrintdescendingCommand(CollectionManager collectionManager) {
        super("print_descending", "print all elements of the collection in descending order");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try{
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            String message = collectionManager.printDescending();
            return new Response("Collection in descending order:\n" + message + "\nElements printed in descending order successfully!");
        } catch (CommandSyntaxIsWrongException e){
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}