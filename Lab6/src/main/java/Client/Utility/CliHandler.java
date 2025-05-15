package Client.Utility;

import Client.Network.Client;
import Client.Utility.PersonGenerator.Creator.PersonCreator;
import Common.Data.Person;
import Common.Network.Request;
import Common.Network.Response;
import Server.Utility.ConsolePrinter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class of input's handler for Person
 */
public class CliHandler {
    private Client client;
    private final Logger logger;
    private PersonCreator personCreator;
    ConsolePrinter consolePrinter = new ConsolePrinter();

    public CliHandler(Client client, Logger logger, PersonCreator personCreator) {
        this.client = client;
        this.logger = logger;
        this.personCreator = personCreator;
    }

    /**
     * Get the string from the user (or script), then make a request to the server
     * and get a response from the server
     * @param command string from user
     * @return response from server
     */
    public Response handle(String[] command) {
        Request request = null;
        switch (command[0]) {
            case "add":
            case "add_if_max":
            case "add_if_min":
                Person newPerson = null;
                if (command.length < 2 || command[1].isEmpty()) {
                    sleep();
                    consolePrinter.printInformation("** ID is automatically initialized successfully **");
                    newPerson = personCreator.createPerson();
                }
                request = new Request(command[0], newPerson);
                break;
            case "sum_of_height":
            case "average_of_height":
            case "clear":
            case "exit":
            case "help":
            case "history":
            case "info":
            case "print_descending":
            case "show":
                request = new Request(command[0], "-1");
                if (command[1].isEmpty()) request = new Request(command[0]);
                break;

            case "execute_script":
                request = new Request(command[0], command[1]);
                break;

            case "remove_by_id":
                Integer idToRemove = parseId(command);
                request = new Request(command[0], idToRemove);
                break;

            case "update":
                Person updatedPerson = null;
                Integer idToUpdate = 0;
                if (!command[1].isEmpty()){
                    try {
                        idToUpdate = Integer.parseInt(command[1]);
                        sleep();
                        consolePrinter.printInformation("** Updating the person... **");
                        updatedPerson = personCreator.createPerson();
                    } catch (NumberFormatException e) {
                        idToUpdate = -1;
                    }
                };
                request = new Request(command[0], idToUpdate, updatedPerson);
                break;

            default:
                request = new Request("NoSuchCommand");
                break;
        }
        return client.sendAndReceiveCommand(request);
    }

    private Integer parseId(String[] command) {
        try {
            return (command.length >= 2 && !command[1].isEmpty())
                    ? Integer.parseInt(command[1])
                    : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    public void sleep() {
        logger.log(Level.INFO, "The command is accepted. Initializing the Person generator... ");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error while initializing ID");
        }
    }
}
