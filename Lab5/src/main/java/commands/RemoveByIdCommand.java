package main.java.commands;

import main.java.data.Person;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * The RemoveByIdCommand class represents a command to remove an element from a collection by its id.
 * This command is used to remove an organization from the collection based on its id.
 */
public class RemoveByIdCommand extends AbstractCommand {

    public RemoveByIdCommand() {
        super("remove_by_id ", "remove an element from a collection by its id");
    }

    public void removeById(String sID) {
        Integer ID;
        try {
            ID = Integer.parseInt(sID);
            if (CollectionManager.idExistence(ID)) {
                Person person = CollectionManager.getPersonByID(ID);
                CollectionManager.historyCommandList.push("remove_by_id");
                CollectionManager.removeElement(ID);
                ConsolePrinter.printResult("The 'remove_by_id' command has been executed successfully!");
                ConsolePrinter.printResult("The person with this ID has been deleted!");
            } else {
                ConsolePrinter.printError("The person with this ID does not exist!");
            }
        } catch (NumberFormatException exception) {
            ConsolePrinter.printError("Invalid command argument!");
        }
    }
    /**
     * Executes the remove_by_id command, removing an element from the collection by its id.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 1) throw new WrongAmountOfElementsException();
        removeById(arg[1]);
    }

    /**
     * Displays information about the remove_by_id command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
