package Server.Utility;

import Common.Network.Request;
import Common.Network.Response;
import Server.Commands.*;
import Server.Manager.Memory.CommandManager;

import java.util.concurrent.Callable;

/**
 * The class is used to process requests and create responses
 */
public class RequestHandler implements Callable<Response> {
    private final CommandManager commandManager;
    private final Request requestFromUser;


    public RequestHandler(CommandManager commandManager, Request requestFromUser) {
        this.commandManager = commandManager;
        this.requestFromUser = requestFromUser;
    }

    /**
     * Process request (execute command and save it into history), after that make a response to client
     * @return response to client
     */
    @Override
    public Response call() {
        AbstractCommand command = commandManager.getByName(requestFromUser.getNameCommand());
        if (command == null) return new Response("That command is not existed.");
        commandManager.addToHistory(requestFromUser.getNameCommand(), requestFromUser.getUser());
        return command.execute(requestFromUser);
    }
}