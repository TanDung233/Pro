package main.java.utility;

import main.java.commands.*;
import main.java.utility.csv.CSVProcess;
import main.java.utility.csv.CSVReader;
import main.java.utility.mode.UserInputMode;

import java.util.Scanner;

/**
 * The {@code Console} class represents a utility for managing commands and interacting with the program via the command line.
 * It initializes the invoker with registered commands and handles user input.
 */
public class Console {
    /**
     * Initializes the invoker with registered commands.
     * Registers all available commands with the invoker.
     */
    public static void starter(String pathToFile) {
        CSVProcess.setPathToFile(pathToFile);
        CollectionManager.getCollectionFromFile(CSVProcess.getPathToFile());
        boolean validInput = false;
        while (!validInput) {
            if (CSVReader.getFlag()) {
                ConsolePrinter.printInformation("Do you want to use the existing data in this file? (yes / no)");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine().trim();
                switch (answer) {
                    case "yes":
                        ConsolePrinter.printResult("The data from file has been loaded!");
                        validInput = true;
                        break;
                    case "no":
                        CollectionManager.initializationCollection();
                        ConsolePrinter.printResult("A new collection has been created!");
                        validInput = true;
                        break;
                    default:
                        ConsolePrinter.printInformation("Invalid input. Please enter 'yes' or 'no'.");
                }
                CommandManager.commandStarter();
                UserInputMode userInputMode = new UserInputMode();
                userInputMode.executeMode();
            } else {
                ConsolePrinter.printResult("Your file is empty!");
                ConsolePrinter.printResult("A new collection has been created!");
                CollectionManager.initializationCollection();
                validInput = true;
                CommandManager.commandStarter();
                UserInputMode userInputMode = new UserInputMode();
                userInputMode.executeMode();

            }
        }
    }
}
