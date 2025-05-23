package Server.Commands;

import Common.Data.Person.Person;
import Common.Data.User;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Exception.PermissionDeniedException;
import Common.Network.Response;
import Common.Network.Request;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.Manager.Memory.CollectionManager;

/**
 * Updates a collection element by its ID.
 */
public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public UpdateCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("update",
                "update the value of a collection element whose id is equal to a given one");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            Integer id = (Integer) request.getArgumentCommand();
            User user = request.getUser();
            if (id == 0) throw new CommandSyntaxIsWrongException();
            if (id == -1) throw new NumberFormatException();
            if (!collectionManager.idExistence(id)) {
                return new Response("No person exists with ID: " + id);
            }
            Person oldDragon = collectionManager.getPersonByID(id);
            if (!oldDragon.getUser().getUserName().equals(request.getUser().getUserName())) throw new PermissionDeniedException();
            collectionManager.removeElement(id);
            Person newPerson = request.getUpdatedPerson();
            newPerson.setId(id);
            collectionManager.addPerson(newPerson,user);
            databaseCollectionManager.updatePersonById(id, newPerson);
            return new Response("That person was successfully updated");
        } catch (CommandSyntaxIsWrongException e) {
            return new Response("Command syntax is not correct. Usage: \"" + getName() + " <id>\"");
        } catch (NumberFormatException e) {
            return new Response("The id must be long");
        } catch (PermissionDeniedException e){
            return new Response("Not enough permissions to remove this element from the collection.");
        }
    }
}