package Client.Utility;

import Client.Network.Client;
import Client.Utility.PersonGenerator.Creator.PersonCreator;
import Common.Data.Person.Person;
import Common.Data.User;
import Common.Network.Request;
import Common.Network.Response;


/**
 * Class of input's handler for Person
 */
public class CliHandler {
    private Client client;
    private PersonCreator personCreator;
    ConsolePrinter consolePrinter = new ConsolePrinter();

    public CliHandler(Client client, PersonCreator personCreator) {
        this.client = client;
        this.personCreator = personCreator;
    }

    /**
     * Get the string from the user (or script), then make a request to the server
     * and get a response from the server
     *
     * @param command string from user
     * @return response from server
     */
    public Response handle(String[] command, User user) {
        Request request = null;
        switch (command[0]) {
            case "add":
            case "add_if_max":
            case "add_if_min":
                Person newPerson = null;
                if (command.length < 2 || command[1].isEmpty()) {
                    consolePrinter.printInformation("** ID is automatically initialized successfully **");
                    newPerson = personCreator.createPerson();
                }
                request = new Request(command[0], newPerson, user);
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
                request = new Request(command[0], "-1", user);
                if (command[1].isEmpty()) request = new Request(command[0], user);
                break;

            case "execute_script":
                request = new Request(command[0], command[1], user);
                break;

            case "remove_by_id":
                Integer idToRemove = parseId(command);
                request = new Request(command[0], idToRemove, user);
                break;

            case "update":
                Person updatedPerson = null;
                Integer idToUpdate = 0;
                if (!command[1].isEmpty()) {
                    try {
                        idToUpdate = Integer.parseInt(command[1]);
                        consolePrinter.printInformation("** Updating the person... **");
                        updatedPerson = personCreator.createPerson();
                    } catch (NumberFormatException e) {
                        idToUpdate = -1;
                    }
                }
                ;
                request = new Request(command[0], idToUpdate, updatedPerson, user);
                break;

            default:
                request = new Request("NoSuchCommand", user);
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
}
