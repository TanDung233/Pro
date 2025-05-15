package Server.Commands;

import Common.Network.Request;
import Common.Network.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Executes and manages registered commands.
 */
public class Invoker {
    private final Map<String, Commands> commands;

    public Invoker() {
        this.commands = new HashMap<>();
    }

    /**
     * Executes the specified command.
     * @param request The request object containing command and arguments
     * @return Response from command execution
     */
    public Response execute(Request request) {
        if (request == null || request.getNameCommand() == null || request.getNameCommand().trim().isEmpty()) {
            return new Response("Invalid request: No command specified");
        }

        try {
            Commands command = commands.get(request.getNameCommand());
            if (command != null) {
                return command.execute(request);
            } else {
                return new Response("Unknown command: " + request.getNameCommand());
            }
        } catch (Exception e) {
            return new Response("Error executing command: " + e.getMessage());
        }
    }
    
    /**
     * Gets all registered commands.
     * @return Map of command names to command instances
     */
    public Map<String, Commands> getCommands() {
        return new HashMap<>(commands);
    }
}