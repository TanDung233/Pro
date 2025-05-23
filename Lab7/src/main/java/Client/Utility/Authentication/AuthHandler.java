package Client.Utility.Authentication;

import Common.Network.Request;
import Common.Data.User;

/**
 * Class to classify type of authentication
 */
public class AuthHandler {
    private final String loginCommand ;
    private final String registerCommand;
    private AuthAsker authAsker;

    public AuthHandler() {
        loginCommand = "login";
        registerCommand = "register";
        this.authAsker = new AuthAsker();
    }

    /**
     * ask type of authentication
     * @return request to server
     */
    public Request handle(){
        String nameCommand = authAsker.askIfRegistered() ? loginCommand : registerCommand;
        User user = new User(authAsker.askUsername(), authAsker.askPassword());
        return new Request(nameCommand, user);
    }
}