package Server.Commands;

import Common.Data.Person;
import Server.Manager.CollectionManager;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;

/**
 * The command to add a person to the collection
 */
public class AddCommand extends Commands {
    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "add a new element to the collection");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) {
        try {
            Person validatedPerson = (Person) request.getArgumentCommand();
            if (validatedPerson == null) throw new CommandSyntaxIsWrongException();
            if(validatedPerson.getId()==0){
                validatedPerson.setId(collectionManager.idmax()+1);
            }
            collectionManager.addPerson(validatedPerson);
            collectionManager.getHistoryCommandList().push("add");
            return new Response("The person has been successfully added!");
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
