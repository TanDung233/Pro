package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.CollectionManager;

/**
 * Removes an element from the collection by its ID.
 */
public class RemoveByIdCommand extends Commands {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "remove an element from a collection by its id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try{
            Integer id = (Integer) request.getArgumentCommand();
            if (id == 0) throw new CommandSyntaxIsWrongException();
            if (id == -1) throw new NumberFormatException();
            if (collectionManager.idExistence(id)) {
                collectionManager.getHistoryCommandList().push("remove_by_id");
                collectionManager.removeElement(id);
                return new Response("The 'remove_by_id' command has been executed successfully!\nThe person with this ID has been deleted!");
            } else {
                return new Response("The person with this ID does not exist!");
            }
        } catch (CommandSyntaxIsWrongException e) {
            return new Response("Command syntax is not correct. Usage: \"" + getName() + " <id>\"");
        } catch (NumberFormatException e) {
            return new Response("The id must be long");
        }
    }
}
