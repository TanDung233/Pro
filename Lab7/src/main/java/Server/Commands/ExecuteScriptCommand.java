package Server.Commands;

import Common.Data.User;
import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.Manager.Memory.CollectionManager;
import Client.Utility.PersonGenerator.Mode.FileScriptMode;

import java.io.FileNotFoundException;

/**
 * The ExecuteScriptCommand class represents a command to read and execute the script from the specified file.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final DatabaseCollectionManager databaseCollectionManager;

    public ExecuteScriptCommand(CollectionManager collectionManager,DatabaseCollectionManager databaseCollectionManager) {
        super("execute_script", "read and execute the script from the specified file.");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            String nameScript = (String) request.getArgumentCommand();
            User user = request.getUser();
            if (nameScript.isEmpty()) throw new CommandSyntaxIsWrongException();
            if (nameScript.equals("-1")) throw new FileNotFoundException();
            FileScriptMode fileScriptMode = new FileScriptMode(
                    nameScript,
                    collectionManager,databaseCollectionManager);
            String message = fileScriptMode.executeMode(user);
            return new Response(message + "\nScript executed successfully: " + nameScript);
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Command syntax is not correct. Usage: \"" + getName() + " [fileName]\"");
        } catch (FileNotFoundException e) {
            return new Response("That script is not exist.");
        }
    }
}