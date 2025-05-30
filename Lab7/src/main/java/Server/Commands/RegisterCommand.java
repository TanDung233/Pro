package Server.Commands;

import Common.Data.User;
import Common.Exception.UserAlreadyExistsException;
import Common.Network.Request;
import Common.Network.Response;
import Common.Network.ProgramCode;
import Server.Manager.Database.DatabaseUserManager;
import Server.Utility.PasswordHasher;

/**
 * Command to register
 */
public class RegisterCommand extends AbstractCommand {
    private DatabaseUserManager databaseUserManager;

    public RegisterCommand(DatabaseUserManager databaseUserManager) {
        super("register", "~hidden");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public Response execute(Request request) {
        String message = "";
        try{
            User user = request.getUser();
            User hashedUser = new User(user.getUserName(), PasswordHasher.hashPassword(user.getPassword()));
            if (!databaseUserManager.insertUser(hashedUser)) throw new UserAlreadyExistsException();
            message = "User \'" + user.getUserName() + "\' with password \'" + "*".repeat(user.getPassword().length())
                    + "\' was registered.";
        } catch (UserAlreadyExistsException e) {
            message = "This user is already exists.";
        }
        return new Response(message, ProgramCode.ERROR);
    }
}