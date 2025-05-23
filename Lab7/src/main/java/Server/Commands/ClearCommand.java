package Server.Commands;

import Common.Data.Person.Person;
import Common.Data.User;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.ProgramCode;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.Manager.Memory.CollectionManager;

/**
 * The ClearCommand class represents a command to clear the collection.
 * It extends the AbstractCommand class.
 */
public class ClearCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final DatabaseCollectionManager databaseCollectionManager;

    public ClearCommand(CollectionManager collectionManager,DatabaseCollectionManager databaseCollectionManager) {
        super("clear", "clear collection");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public Response execute(Request request) {
        String message = "";
        ProgramCode code = null;
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            User user = request.getUser();
            for (Person person : collectionManager.getCollection()){
                if (person.getUser().getUserName().equals(user.getUserName())){
                    databaseCollectionManager.deletePersonById(person.getId());
                    collectionManager.removeFromCollection(person);
                }
            }
            return new Response("The 'clear' command has been executed successfully!\nCollection cleared!");
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}