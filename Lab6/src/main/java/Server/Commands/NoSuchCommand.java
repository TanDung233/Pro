package Server.Commands;

import Common.Network.Request;
import Common.Network.Response;

/**
 * Command is not available
 */
public class NoSuchCommand extends Commands {

    public NoSuchCommand() {
        super("NoSuchCommand", "");
    }

    @Override
    public Response execute (Request request){
        String message = "That command is not available. Please enter \"help\" for helping";
        return new Response(message);
    }
}