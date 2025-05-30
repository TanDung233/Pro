package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Memory.CollectionManager;
import Server.Manager.Memory.CommandManager;

/**
 * The HistoryCommand class displays the last 11 executed commands.
 */
public class HistoryCommand extends AbstractCommand {
    private final CommandManager commandManager;

    public HistoryCommand(CommandManager commandManager) {
        super("history", "display the last 11 executed commands (without arguments)");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            String message = "<List of 11 last used commands>\n" +
                    commandManager.getCommandHistory().toString();
            return new Response(message);
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
