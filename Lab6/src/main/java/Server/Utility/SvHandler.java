package Server.Utility;

import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.StandardCommandManager;
import Server.Commands.*;
import java.util.logging.Logger;

/**
 * The class is used to process requests and create responses
 */
public class SvHandler {
    private final StandardCommandManager commandManager;
    private final Logger logger;


    public SvHandler(StandardCommandManager commandManager, Logger logger) {
        this.commandManager = commandManager;
        this.logger = logger;
    }


    /**
     * Process request (execute command and save it into history), after that make a response to client
     * @param request request from client
     * @return response to client
     */
    public Response handle(Request request) {
        Commands command = commandManager.getByName(request.getNameCommand());
        if (command == null) return new Response("That command is not existed.");
        return command.execute(request);
    }
}