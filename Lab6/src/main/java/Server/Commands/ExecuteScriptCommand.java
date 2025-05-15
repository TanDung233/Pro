package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.CollectionManager;
import Client.Utility.PersonGenerator.Mode.FileScriptMode;

import java.io.FileNotFoundException;

/**
 * The ExecuteScriptCommand class represents a command to read and execute the script from the specified file.
 */
public class ExecuteScriptCommand extends Commands {
    private final CollectionManager collectionManager;

    public ExecuteScriptCommand(CollectionManager collectionManager) {
        super("execute_script", "read and execute the script from the specified file.");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            String nameScript = (String) request.getArgumentCommand();
            if (nameScript.isEmpty()) throw new CommandSyntaxIsWrongException();
            if (nameScript.equals("-1")) throw new FileNotFoundException();
            FileScriptMode fileScriptMode = new FileScriptMode(
                    nameScript,
                    collectionManager);
            String message = fileScriptMode.executeMode();
            collectionManager.getHistoryCommandList().push("execute_script");
            return new Response(message + "\nScript executed successfully: " + nameScript);
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Command syntax is not correct. Usage: \"" + getName() + " [fileName]\"");
        } catch (FileNotFoundException e) {
            return new Response("That script is not exist.");
        }
    }
}