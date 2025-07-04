package Server.Commands;

import Common.Exception.CommandSyntaxIsWrongException;
import Common.Network.ProgramCode;
import Common.Network.Request;
import Common.Network.Response;
/**
 * The ExitCommand class represents a command to end the program without saving to a file.
 */
public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", "end the program (without saving to a file).");
    }

    @Override
    public Response execute(Request request) {
        try {
            if (request.getArgumentCommand() != null) throw new CommandSyntaxIsWrongException();
            return new Response("The program was stopped", ProgramCode.CLIENT_EXIT);
        } catch (CommandSyntaxIsWrongException exception) {
            return new Response("Syntax command is not correct. Usage: \"" + getName() + "\"");
        }
    }
}
