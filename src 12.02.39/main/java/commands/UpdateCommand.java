package main.java.commands;

import main.java.data.Person;
import main.java.exceptions.EmptyCollectionException;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.exceptions.WrongInputInScriptException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonCreator;

import java.util.Scanner;

/**
 * Updates a collection element by its ID.
 */
public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ConsolePrinter consolePrinter;
    private final PersonCreator personCreator;

    public UpdateCommand(CollectionManager collectionManager,
                         ConsolePrinter consolePrinter,
                         PersonCreator personCreator) {
        super("update id {element}",
                "update the value of a collection element whose id is equal to a given one");
        this.collectionManager = collectionManager;
        this.consolePrinter = consolePrinter;
        this.personCreator = personCreator;
    }

    private void updateElement(String idString, Scanner scanner) {
        try {
            if (collectionManager.getCollection().isEmpty()) {
                throw new EmptyCollectionException();
            }

            int id = Integer.parseInt(idString);
            if (!collectionManager.idExistence(id)) {
                consolePrinter.printError("No person exists with ID: " + id);
                return;
            }

            Person updatedPerson = personCreator.createPerson(scanner);
            collectionManager.updateElement(updatedPerson, id);
            collectionManager.getHistoryCommandList().push("update");

            consolePrinter.printResult(String.format(
                    "Successfully updated person with ID %d: %s",
                    id,
                    updatedPerson.getName()
            ));

        } catch (NumberFormatException e) {
            consolePrinter.printError("Invalid ID format. Please provide a valid integer ID.");
        } catch (EmptyCollectionException e) {
            consolePrinter.printError("Cannot update - collection is empty.");
        } catch (WrongInputInScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(String[] args) throws WrongAmountOfElementsException {
        if (args.length != 2) throw new WrongAmountOfElementsException();
        updateElement(args[1], new Scanner(System.in));
    }

    @Override
    public void getCommandInformation() {
        consolePrinter.printInformation(super.toString());
    }
}