package Server.Commands;

import Common.Data.Person.Person;
import Common.Data.User;
import Common.Exception.FailureToCreateObjectException;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.Manager.Memory.CollectionManager;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;

/**
 * The command to add a person to the collection
 */
public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public AddCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("add", "add a new element to the collection");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }


    @Override
    public Response execute(Request request) {
        try {
            Person validatedPerson = (Person) request.getArgumentCommand();
            User user = request.getUser();
            if (validatedPerson == null) throw new CommandSyntaxIsWrongException();
            if (!databaseCollectionManager.insertPerson(validatedPerson, user)) throw new FailureToCreateObjectException();
            collectionManager.addPerson(validatedPerson,user);
            return new Response("The person has been successfully added!");
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
