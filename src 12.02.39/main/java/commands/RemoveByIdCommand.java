package main.java.commands;

import main.java.data.Person;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

/**
 * Removes an element from the collection by its ID.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;

    public RemoveByIdCommand(CollectionManager collectionManager,
                             ConsolePrinter consolePrinter) {
        super("remove_by_id", "remove an element from a collection by its id");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
    }

    public void removeById(String sID) {
        Integer ID;
        try {
            ID = Integer.parseInt(sID);
            if (collectionManager.idExistence(ID)) {
                collectionManager.getHistoryCommandList().push("remove_by_id");
                collectionManager.removeElement(ID);
                consolePrinter.printResult("The 'remove_by_id' command has been executed successfully!");
                consolePrinter.printResult("The person with this ID has been deleted!");
            } else {
                consolePrinter.printError("The person with this ID does not exist!");
            }
        } catch (NumberFormatException exception) {
            consolePrinter.printError("Invalid command argument!");
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
        if (arg.length != 2) throw new WrongAmountOfElementsException();
        removeById(arg[1]);
    }
    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}