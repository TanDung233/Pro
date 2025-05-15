package main.java.utility;

import main.java.commands.*;
import main.java.utility.creator.IDGenerator;
import main.java.utility.creator.PersonBuilder;
import main.java.utility.creator.PersonCreator;
import main.java.utility.csv.CSVProcess;
import main.java.utility.csv.CSVReader;
import main.java.utility.csv.CSVWriter;
import main.java.utility.mode.UserInputMode;

import java.io.File;
import java.util.Scanner;

/**
 * The {@code Console} class represents a utility for managing commands and interacting with the program via the command line.
 * It initializes the invoker with registered commands and handles user input.
 */
public class Console {

    ConsolePrinter consolePrinter = new ConsolePrinter();
    Scanner scanner = new Scanner(System.in);
    CSVReader csvReader = new CSVReader();
    CSVWriter csvWriter = new CSVWriter();
    CSVProcess csvProcess = new CSVProcess(csvReader, csvWriter);
    CollectionManager collectionManager = new CollectionManager(consolePrinter, csvProcess);
    IDGenerator idGenerator = new IDGenerator(collectionManager);
    PersonBuilder personBuilder = new PersonBuilder(scanner);
    PersonCreator personCreator = new PersonCreator(personBuilder, idGenerator, consolePrinter);
    Invoker invoker = new Invoker(consolePrinter);
    CommandManager commandManager = new CommandManager(
            collectionManager,
            consolePrinter,
            personCreator,
            personBuilder,
            invoker
    );

    /**
     * Initializes the invoker with registered commands.
     * Registers all available commands with the invoker.
     */
    public void starter(String pathToFile) {
        if (csvProcess == null) {
            consolePrinter.printError("CSV processor is not initialized!");
            return;
        }

        File file = new File(pathToFile);
        if (!file.exists() || file.length() == 0) {
            consolePrinter.printResult("File is empty or does not exist. Creating new collection.");
            collectionManager.initializationCollection();
            commandManager.load();
            new UserInputMode(invoker, consolePrinter).executeMode();
            return;
        }

        csvProcess.setFilePath(pathToFile);

        consolePrinter.printInformation("Do you want to use the existing data in this file? (yes/no)");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String answer = scanner.nextLine().trim().toLowerCase();
            if ("yes".equals(answer)) {
                try {
                    csvProcess.loadCollection(pathToFile);
                    consolePrinter.printResult("Data loaded successfully!");
                    collectionManager.getCollectionFromFile(pathToFile);
                    break;
                } catch (Exception e) {
                    consolePrinter.printError("Failed to load data: " + e.getMessage());
                }
            } else if ("no".equals(answer)) {
                collectionManager.initializationCollection();
                consolePrinter.printResult("New collection created!");
                break;
            } else {
                consolePrinter.printInformation("Please enter 'yes' or 'no':");
            }
        }

        commandManager.load();
        new UserInputMode(invoker, consolePrinter).executeMode();
    }
}
