package Server.Commands;

import Common.Data.Person;
import Common.Exception.WrongAmountOfElementsException;
import Common.Exception.WrongInputInScriptException;
import Server.Manager.CollectionManager;
import Server.Utility.ConsolePrinter;
import Client.Utility.PersonGenerator.Creator.PersonCreator;

import java.util.Scanner;
import Common.Data.Person;
import Server.Manager.CollectionManager;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;


/**
 * Command to add a new dragon if his age is maximum in the collection
 */
public class AddIfMinCommand extends Commands {
    private CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min","add a new element to a collection if its value is less than the value of the smallest element of this collection");
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
            collectionManager.addIfMin(validatedPerson);
            collectionManager.getHistoryCommandList().push("add_if_min");
            return new Response("The 'add_if_min' command has been executed successfully!");
        } catch (CommandSyntaxIsWrongException e ) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
