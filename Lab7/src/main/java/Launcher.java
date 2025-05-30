import Client.ClientApp;
import Common.Exception.CommandSyntaxIsWrongException;
import Server.ServerApp;

/**
 * Main class
 */
public class Launcher {
    public static void main(String[] args) {
        try {
            String mainClass = args[0];

            if (!mainClass.equals("ClientApp") && !mainClass.equals("ServerApp")) {
                throw new CommandSyntaxIsWrongException();
            }
            if (args.length > 1) throw new CommandSyntaxIsWrongException();
            switch (mainClass) {
                case "ClientApp":
                    ClientApp.main(new String[]{});
                    break;
                case "ServerApp":
                    ServerApp.main(new String[]{});
                    break;
                default:
                    System.out.println("Unknown main class: " + mainClass);
            }
        } catch (CommandSyntaxIsWrongException e) {
            System.out.println("Usage: java -jar Lab_7.jar [ClientApp / ServerApp]");
            return;
        }
    }
}