package Server.Commands;

import Common.Data.Person;
import Server.Manager.CollectionManager;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;


/**
 * Command to add a new dragon if his age is maximum in the collection
 */
public class AddIfMaxCommand extends Commands {
    private CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        super("add_if_max", "add a new element to the collection, if its value is greater than the value of the largest element of this collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute (Request request) {
        try {
            Person validatedPerson = (Person) request.getArgumentCommand();
            if (validatedPerson.getId() == null){
                validatedPerson.setId(collectionManager.idmax() + 1) ;
            }
            if (validatedPerson == null) throw new CommandSyntaxIsWrongException();
            collectionManager.addIfMax(validatedPerson);
            collectionManager.getHistoryCommandList().push("add_if_max");
            return new Response("The 'add_if_max' command has been executed successfully!");
        } catch (CommandSyntaxIsWrongException e ) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
