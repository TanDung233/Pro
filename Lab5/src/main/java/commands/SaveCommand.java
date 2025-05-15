package main.java.commands;

import main.java.exceptions.WrongAmountOfElementsException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;

import java.util.Scanner;

/**
 * The SaveCommand class represents a command to save the collection to a file.
 * This command triggers the saving of the collection data to a file.
 */
public class SaveCommand extends AbstractCommand {
    public SaveCommand() {
        super("save", "save the collection to the file");
    }

    public void save() {
        CollectionManager.historyCommandList.push("save");
        ConsolePrinter.printResult("The 'save' command has been executed successfully!");
        ConsolePrinter.printResult("The collection has been saved in the file!");
        CollectionManager.saveCollectionToFile();
    }

    /**
     * Executes the save command, triggering the saving of the collection to a file.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        save();
    }

    /**
     * Displays information about the save command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
