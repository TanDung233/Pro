package Client.Utility.Authentication;

import Client.Utility.ConsolePrinter;
import java.util.Scanner;

/**
 * Class for asking username and password
 */
public class AuthAsker{
    ConsolePrinter consolePrinter = new ConsolePrinter();
    /**
     * ask username
     * @return username
     */
    public String askUsername(){
        String login;
        while (true) {
            try{
                consolePrinter.printInformation("Enter username: ");
                login = scanner();
                if (login.isEmpty()) throw new Exception();
                break;
            } catch (Exception e){
                consolePrinter.printError("The login must not empty!");
            }
        }
        return login;
    }

    /**
     * Ask password
     * @return password
     */
    public String askPassword(){
        String password;
        while (true) {
            try{
                consolePrinter.printInformation("Enter password: ");
                password = scanner();
                if (password.isEmpty()) throw new Exception();
                break;
            } catch (Exception e){
                consolePrinter.printError("The password must not empty!");
            }
        }
        return password;
    }

    /**
     * ask if user is registed
     * @return answer
     */
    public boolean askIfRegistered(){
        String question = "Do you have an account? (\"Yes\" or \"No\")";
        String answer;
        while(true){
            try{
                consolePrinter.printInformation(question);
                System.out.println("> ");
                answer = scanner().toLowerCase();
                if (!answer.equals("yes") && !answer.equals("no")) throw new Exception();
                if (answer.equals("no")) consolePrinter.printInformation("Create new user:\n");
                break;
            } catch (Exception e) {
                consolePrinter.printError("The syntax is not correct !");
            }
        }
        return answer.equals("yes");
    }

    /**
     * Get string from input
     * @return string
     */
    private String scanner(){
        return new Scanner(System.in).nextLine().trim();
    }


}