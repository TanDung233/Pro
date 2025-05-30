package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Memory.CommandManager;

import java.util.stream.Collectors;

/**
 * Command to show the syntax of full commands
 */
public class HelpCommand extends AbstractCommand {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", "display help on available commands");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute (Request request){
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            String message = commandManager.getCommands().stream().
                    filter(command -> !command.getDescription().equals("~hidden"))
                    .map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription() ))
                    .collect(Collectors.joining());
            return new Response(message);
        } catch (CommandSyntaxIsWrongException e) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}