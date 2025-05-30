package Client.Utility;

import Client.Network.Client;
import Client.Utility.Authentication.AuthHandler;
import Client.Utility.PersonGenerator.Creator.PersonBuilder;
import Client.Utility.PersonGenerator.Creator.PersonCreator;
import Common.Data.User;
import Common.Exception.CommandNotFoundException;
import Common.Network.Request;
import Common.Network.Response;
import Common.Network.ProgramCode;

import java.util.*;


/**
 * Class of program's operation
 */
public class CliRunner implements Runnable {
    private final Client client;
    private final CliHandler cliHandler;
    private final AuthHandler authHandler;
    private final PersonCreator personCreator;
    private final PersonBuilder personBuilder;
    ConsolePrinter consolePrinter = new ConsolePrinter();
    private User user;


    public CliRunner(Client client) {
        this.client = client;
        this.personBuilder = new PersonBuilder(new Scanner(System.in));
        this.personCreator = new PersonCreator(personBuilder);
        this.cliHandler = new CliHandler(client, personCreator);
        this.authHandler = new AuthHandler();
    }

    /**
     * Run
     */
    @Override
    public void run(){
        client.connectToServer();
        processAuthentication();
        processRequestToServer();
        client.disconnectToServer();
    }

    /**
     * Process authentication
     */
    private void processAuthentication(){
        Request requestToServer = null;
        Response responseFromServer = null;
        do{
            try{
                requestToServer = authHandler.handle();
                if (requestToServer == null) continue;
                responseFromServer = client.sendAndReceiveCommand(requestToServer);
                if (responseFromServer.getMessage() == null) throw new Exception();
                consolePrinter.printResult(responseFromServer);
            } catch (Exception e) {
                consolePrinter.printError("Mistake while trying to authenticate user.");
            }
        } while (responseFromServer == null || !responseFromServer.getResponseCode().equals(ProgramCode.OK));
        user = requestToServer.getUser();
    }

    /**
     * Process request to server
     */
    private void processRequestToServer(){
        ProgramCode commandStatus = null;
        do {
            try{
                String[] command = argumentToCommand((new Scanner(System.in)).nextLine());

                commandStatus = updateProgramStatus(command);
                
                if (commandStatus != ProgramCode.ERROR) {
                    Response response = cliHandler.handle(command, user);
                    if (response == null) continue;
                    consolePrinter.printResult("** Response from server: ");
                    consolePrinter.printResult(response);
                }

            } catch (CommandNotFoundException e) {
                consolePrinter.printError("This command is not exist. Enter 'help' for helping");
            }
        } while (commandStatus != ProgramCode.CLIENT_EXIT);
    }

    /**
     * Update program's status after get a command from input
     * @param command command
     * @return program's status
     */
    private ProgramCode updateProgramStatus (String[] command)  {
        try {
            switch (command[0]){
                case "exit":
                    if (command[1].isEmpty()) return ProgramCode.CLIENT_EXIT;
            }
            return ProgramCode.OK;
        } catch (CommandNotFoundException e) {
            if (!command[0].isEmpty()){
                consolePrinter.printError("Command " + command[0] + " is not exist. Enter 'help' for helping");
            } else {
                System.out.print("");
            }
        }
        return ProgramCode.ERROR;
    }


    /**
     * Process a string to array with length 2
     *
     * @param argument a string
     * @return array
     */
    private String[] argumentToCommand(String argument) {
        String[] command = {};
        try {
            command = (argument.trim() + " ").split(" ", 2);
            if (command[0].isEmpty()) throw new NoSuchElementException();
            if (command.length > 1) command[1] = command[1].trim();
        } catch (NoSuchElementException exception) {
            consolePrinter.printInformation("");
        }
        return command;
    }
}