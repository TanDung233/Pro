package main.java.commands;

import main.java.data.Person;
import main.java.exceptions.EmptyCollectionException;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonCreator;

import java.util.Scanner;

/**
 * The UpdateCommand class represents a command to update a collection element by its ID.
 * This command updates the value of a collection element whose ID is equal to a given one.
 */
public class UpdateCommand extends AbstractCommand {

    public UpdateCommand() {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
    }

    public static void update(String sID, Scanner scanner) {
        int ID;
        try {
            if (CollectionManager.getCollection().size() == 0) throw new EmptyCollectionException();
            ID = Integer.parseInt(sID);
            if (CollectionManager.idExistence(ID)) {
                CollectionManager.historyCommandList.push("update");
                Person newPerson = PersonCreator.personCreator(scanner);
                CollectionManager.updateElement(newPerson, ID);
                ConsolePrinter.printResult("The 'update' command has been executed successfully");
                ConsolePrinter.printResult("Person id " + ID + " is updated!");
            } else {
                ConsolePrinter.printError("The person with this ID does not exist in the collection!");
            }
        } catch (NumberFormatException exception) {
            ConsolePrinter.printError("The ID is not correct!");
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("The collection is empty");
        }
    }

    /**
     * Executes the update command, updating the value of a collection element by its ID.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 2) throw new WrongAmountOfElementsException();
        update(arg[1], new Scanner(System.in));
    }

    /**
     * Displays information about the update command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
