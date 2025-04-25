package main.java.utility.mode;

import main.java.commands.*;
import main.java.exceptions.WrongAmountOfElementsException;
import main.java.exceptions.WrongInputInScriptException;
import main.java.utility.CollectionManager;
import main.java.utility.ConsolePrinter;
import main.java.utility.creator.PersonBuilder;
import main.java.utility.creator.PersonCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileScriptMode implements IMode {
    private final String scriptPath;
    private final ConsolePrinter consolePrinter;
    private final CollectionManager collectionManager;
    private final PersonBuilder personBuilder;
    private final PersonCreator personCreator;
    private final Invoker invoker;

    private Scanner scriptScanner;
    private final List<String> executedScripts = new ArrayList<>();
    private boolean executionEnabled = true;
    private int executionCount = 1;
    private int maxExecutions = 1;

    public FileScriptMode(String scriptPath,
                          ConsolePrinter consolePrinter,
                          CollectionManager collectionManager,
                          PersonBuilder personBuilder,
                          PersonCreator personCreator,

    Invoker invoker) {
        this.scriptPath = scriptPath;
        this.consolePrinter = consolePrinter;
        this.collectionManager = collectionManager;
        this.personBuilder = personBuilder;
        this.personCreator = personCreator;
        this.invoker = invoker;
    }

    @Override
    public void executeMode() {
        if (executedScripts.contains(scriptPath)) {
            consolePrinter.printError("Recursive script detected: " + scriptPath);
            return;
        }

        executedScripts.add(scriptPath);
        Scanner originalScanner = personBuilder.getUserScanner();

        try {
            initializeScriptScanner();
            personBuilder.setUserScanner(scriptScanner);
            personBuilder.setFileMode();

            while (executionEnabled && scriptScanner.hasNextLine()) {
                String commandLine = getNextCommandLine();
                if (!commandLine.trim().isEmpty()) {
                    processCommandLine(commandLine);
                }
            }
        } catch (FileNotFoundException e) {
            consolePrinter.printError("Script file not found: " + scriptPath);
        } catch (NoSuchElementException e) {
            consolePrinter.printError("Script file is empty: " + scriptPath);
        } catch (WrongInputInScriptException e) {
            throw new RuntimeException(e);
        } catch (WrongAmountOfElementsException e) {
            throw new RuntimeException(e);
        } finally {
            personBuilder.setUserScanner(originalScanner);
            personBuilder.setUserMode();
            executedScripts.remove(scriptPath);
        }
    }

    private void initializeScriptScanner() throws FileNotFoundException {
        scriptScanner = new Scanner(new File(scriptPath));
        if (!scriptScanner.hasNextLine()) {
            throw new NoSuchElementException();
        }
    }

    private String getNextCommandLine() {
        String line = scriptScanner.nextLine().trim();
        consolePrinter.printInformation("> " + line);
        return line;
    }

    private void processCommandLine(String commandLine) throws WrongInputInScriptException, WrongAmountOfElementsException {
        String[] parts = commandLine.split("\\s+", 2);
        String commandName = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandName) {
            case "add":
                new AddCommand(collectionManager,
                        personCreator,
                        consolePrinter)
                        .execute(new String[]{commandName, arguments});
                break;
            case "update":
                new UpdateCommand(collectionManager,
                        consolePrinter,
                        personCreator)
                        .execute(new String[]{commandName, arguments});
                break;

            case "add_if_max":
                new AddIfMaxCommand(collectionManager,
                        personCreator,
                        consolePrinter)
                        .execute(new String[]{commandName});
                break;

            case "add_if_min":
                new AddIfMinCommand(collectionManager,
                        personCreator,
                        consolePrinter)
                        .execute(new String[]{commandName});
                break;

            case "execute_script":
                handleNestedScript(arguments);
                break;

            default:
                invoker.execute(new String[]{commandName, arguments});
        }
    }

    private void handleNestedScript(String nestedScriptPath) {
        if (executedScripts.contains(nestedScriptPath)) {
            consolePrinter.printError("Recursive script prevented: " + nestedScriptPath);
            return;
        }

        if (executionCount >= maxExecutions) {
            consolePrinter.printError("Maximum execution count reached for script: " + nestedScriptPath);
            return;
        }

        consolePrinter.printResult("Executing nested script: " + nestedScriptPath);
        FileScriptMode nestedScript = new FileScriptMode(
                nestedScriptPath,
                consolePrinter,
                collectionManager,
                personBuilder,
                personCreator,
                invoker
        );

        nestedScript.setMaxExecutions(this.maxExecutions);
        nestedScript.setExecutionCount(this.executionCount + 1);
        nestedScript.executeMode();
    }

    private void setMaxExecutions(int max) {
        this.maxExecutions = max;
    }

    private void setExecutionCount(int count) {
        this.executionCount = count;
    }

    @Override
    public Scanner getScanner() {
        return scriptScanner;
    }
}