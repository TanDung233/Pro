package Server.Commands;

import Common.Data.Person.Person;
import Common.Data.User;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Exception.IdNotFoundException;
import Common.Exception.PermissionDeniedException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.Manager.Memory.CollectionManager;

/**
 * Removes an element from the collection by its ID.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_by_id", "remove an element from a collection by its id");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public Response execute(Request request) {
        try{
            Integer id = (Integer) request.getArgumentCommand();
            User user = request.getUser();
            if (id == 0) throw new CommandSyntaxIsWrongException();
            if (id == -1) throw new NumberFormatException();
            Person person = collectionManager.getPersonByID(id);
            if (person == null) throw new IdNotFoundException();
            if (!person.getUser().getUserName().equals(user.getUserName())) throw new PermissionDeniedException();
            if (collectionManager.idExistence(id)) {
                databaseCollectionManager.deletePersonById(id);
                databaseCollectionManager.deleteCoordinatesByPersonId(id);
                databaseCollectionManager.deleteLocationByPersonId(id);
                collectionManager.removeElement(id);
                return new Response("The 'remove_by_id' command has been executed successfully!\nThe person with this ID has been deleted!");
            } else {
                return new Response("The person with this ID does not exist!");
            }

        } catch (CommandSyntaxIsWrongException e) {
            return new Response("Command syntax is not correct. Usage: \"" + getName() + " <id>\"");
        } catch (IdNotFoundException e){
            return new Response("This id is not existed");
        } catch (NumberFormatException e) {
            return new Response("The id must be Integer");
        } catch (PermissionDeniedException e){
            return new Response("Not enough permissions to remove this element from the collection.");
        }
    }
}
