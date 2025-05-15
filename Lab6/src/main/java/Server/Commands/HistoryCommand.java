package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.CollectionManager;

/**
 * The HistoryCommand class displays the last 11 executed commands.
 */
public class HistoryCommand extends Commands {
    private final CollectionManager collectionManager;

    public HistoryCommand(CollectionManager collectionManager) {
        super("history", "display the last 11 executed commands (without arguments)");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            String messsage = collectionManager.printLast11Commands();
            collectionManager.getHistoryCommandList().push("history");
            return new Response(messsage + "\nThe 'history' command has been executed successfully!");
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
