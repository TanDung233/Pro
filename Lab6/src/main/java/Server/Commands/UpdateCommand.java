package Server.Commands;

import Common.Data.Person;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Exception.EmptyCollectionException;
import Common.Exception.WrongAmountOfElementsException;
import Common.Network.Response;
import Common.Network.Request;
import Server.Manager.CollectionManager;
import Server.Utility.ConsolePrinter;
import Client.Utility.PersonGenerator.Creator.PersonCreator;

import java.util.Scanner;

/**
 * Updates a collection element by its ID.
 */
public class UpdateCommand extends Commands {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update",
                "update the value of a collection element whose id is equal to a given one");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            Integer id = (Integer) request.getArgumentCommand();
            if (id == 0) throw new CommandSyntaxIsWrongException();
            if (id == -1) throw new NumberFormatException();
            if (!collectionManager.idExistence(id)) {
                return new Response("No person exists with ID: " + id);
            }
            collectionManager.removeElement(id);
            Person newPerson = request.getUpdatedPerson();
            newPerson.setId(id);
            collectionManager.addPerson(newPerson);
            return new Response("That person was successfully updated");
        } catch (CommandSyntaxIsWrongException e) {
            return new Response("Command syntax is not correct. Usage: \"" + getName() + " <id>\"");
        } catch (NumberFormatException e) {
            return new Response("The id must be long");
        }
    }
}