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
 * Command to add a new dragon if his age is maximum in the collection
 */
public class AddIfMaxCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager,DatabaseCollectionManager databaseCollectionManager) {
        super("add_if_max", "add a new element to the collection, if its value is greater than the value of the largest element of this collection");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public Response execute (Request request) {
        try {
            Person validatedPerson = (Person) request.getArgumentCommand();
            User user = request.getUser();
            if (validatedPerson == null) throw new CommandSyntaxIsWrongException();
            if (!databaseCollectionManager.insertPerson(validatedPerson, user)) throw new FailureToCreateObjectException();
            collectionManager.addIfMax(validatedPerson,user);
            return new Response("The 'add_if_max' command has been executed successfully!");
        } catch (CommandSyntaxIsWrongException e ) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
