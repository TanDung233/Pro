package Client.Utility.PersonGenerator.Mode;

import Common.Data.Person.Person;
import Common.Data.User;
import Common.Network.Request;
import Common.Network.Response;
import Server.Commands.*;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.Manager.Memory.CollectionManager;
import Server.Manager.Memory.CommandManager;
import Client.Utility.ConsolePrinter;
import Client.Utility.PersonGenerator.Creator.PersonBuilder;
import Client.Utility.PersonGenerator.Creator.PersonCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileScriptMode implements IMode {
    private final String scriptPath;
    private final CollectionManager collectionManager;
    private final PersonBuilder personBuilder;
    private final PersonCreator personCreator;
    private final DatabaseCollectionManager databaseCollectionManager;


    private Scanner scriptScanner;
    private final List<String> executedScripts = new ArrayList<>();
    private boolean executionEnabled = true;
    private int executionCount = 1;
    private int maxExecutions = 1;
    ConsolePrinter consolePrinter = new ConsolePrinter();

    public FileScriptMode(String scriptPath, CollectionManager collectionManager,DatabaseCollectionManager databaseCollectionManager ) {
        this.scriptPath = scriptPath;
        this.collectionManager = collectionManager;
        this.personBuilder = new PersonBuilder(new Scanner(System.in));
        this.personCreator = new PersonCreator(personBuilder);
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public String executeMode(User user) {
        StringBuilder output = new StringBuilder();
        if (executedScripts.contains(scriptPath)) {
            return ("Recursive script detected: " + scriptPath);
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
                    output.append("> ").append(commandLine).append("\n");
                    try {
                        Response response = processCommandLine(commandLine,user);
                        output.append(response.getMessage()).append("\n");
                    } catch (Exception e) {
                        output.append("Error: ").append(e.getMessage()).append("\n\n");
                    }                }
            }
        } catch (FileNotFoundException e) {
            consolePrinter.printError("Script file not found: " + scriptPath);
        } catch (NoSuchElementException e) {
            consolePrinter.printError("Script file is empty: " + scriptPath);

        } finally {
            personBuilder.setUserScanner(originalScanner);
            personBuilder.setUserMode();
            executedScripts.remove(scriptPath);
        }
        return output.toString();
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

    private Response processCommandLine(String commandLine, User user) {
        String[] parts = commandLine.split("\\s+", 2);
        String commandName = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";
        switch (commandName) {
            case "add":
                Person personToAdd = personCreator.createPerson();
                Request request = new Request(commandName, personToAdd, user);
                return new  AddCommand(collectionManager,databaseCollectionManager).execute(request);

            case "update":
                Integer idToUpdate;
                idToUpdate = Integer.parseInt(arguments);
                consolePrinter.printInformation("** Updating the person... **");
                Person updatedPerson = personCreator.createPerson();
                Request request1 = new Request(commandName, idToUpdate, updatedPerson,user);
                return new  UpdateCommand(collectionManager,databaseCollectionManager).execute(request1);

            case "add_if_max":
                Person personToAddMax = personCreator.createPerson();
                Request request2 = new Request(commandName, personToAddMax,user);
                return new  AddIfMaxCommand(collectionManager,databaseCollectionManager).execute(request2);

            case "add_if_min":
                Person personToAddMin = personCreator.createPerson();
                Request request3 = new Request(commandName, personToAddMin,user);
                return new  AddIfMinCommand(collectionManager,databaseCollectionManager).execute(request3);

            case "average_of_height":
                return new AverageCommand(collectionManager).execute(new Request(commandName,user));

            case "clear":
                return new ClearCommand(collectionManager,databaseCollectionManager).execute(new Request(commandName,user));

            case "exit":
                return new ExitCommand().execute(new Request(commandName,user));

            case "info":
                return new InfoCommand(collectionManager).execute(new Request(commandName,user));

            case "print_descending":
                return new PrintdescendingCommand(collectionManager).execute(new Request(commandName,user));

            case "show":
                return new ShowCommand(collectionManager).execute(new Request(commandName,user));

            case "sum":
                return new SumCommand(collectionManager).execute(new Request(commandName,user));

            case "remove_by_id":
                Integer idToRemove;
                idToRemove = Integer.parseInt(arguments);
                Request request4 = new Request(commandName, idToRemove,user);
                return new  RemoveByIdCommand(collectionManager,databaseCollectionManager).execute(request4);

            case "execute_script":
                handleNestedScript(arguments,user);

            default:
                return new Response("Unknown command: " + commandName);
        }
    }

    private void handleNestedScript(String nestedScriptPath, User user) {
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
                collectionManager,databaseCollectionManager);

        nestedScript.setMaxExecutions(this.maxExecutions);
        nestedScript.setExecutionCount(this.executionCount + 1);
        nestedScript.executeMode(user);
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