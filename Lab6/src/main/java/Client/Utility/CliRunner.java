package Client.Utility;

import Client.Network.Client;
import Client.Utility.PersonGenerator.Creator.PersonBuilder;
import Client.Utility.PersonGenerator.Creator.PersonCreator;
import Common.Exception.CommandNotFoundException;
import Common.Network.Response;
import Server.Utility.ConsolePrinter;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class of program's operation
 */
public class CliRunner implements Runnable {
    private final Client client;
    private final Logger logger;
    private final CliHandler cliHandler;
    private final PersonCreator personCreator;
    private final PersonBuilder personBuilder;
    private boolean isScriptFound = true;
    ConsolePrinter consolePrinter = new ConsolePrinter();


    public CliRunner(Client client, Logger logger) {
        this.client = client;
        this.logger = logger;
        this.personBuilder = new PersonBuilder(new Scanner(System.in));
        this.personCreator = new PersonCreator(personBuilder);
        this.cliHandler = new CliHandler(client, logger, personCreator);
    }

    @Override
    public void run() {
        ProgramStatus commandStatus = null;
        do {
            isScriptFound = true;
            try {
                Scanner scanner = new Scanner(System.in);
                String[] command = argumentToCommand(scanner.nextLine());

                commandStatus = updateProgramStatus(command);

                if (!isScriptFound) {
                    command[1] = "-1";
                }

                if (commandStatus != ProgramStatus.ERROR) {
                    Response response = cliHandler.handle(command);
                    if (response == null) continue;
                    consolePrinter.printInformation(response);
                }

            } catch (CommandNotFoundException e) {
                consolePrinter.printError("This command is not exist. Enter 'help' for helping");
            }
        } while (commandStatus != ProgramStatus.EXIT);
        client.disconnectToServer();
        logger.log(Level.INFO, "Program was ended.");
    }

    /**
     * Update program's status after get a command from input
     *
     * @param command command
     * @return program's status
     */
    private ProgramStatus updateProgramStatus(String[] command) {
        try {
            switch (command[0]) {
                case "exit":
                    if (command[1].isEmpty()) return ProgramStatus.EXIT;
            }
            return ProgramStatus.RUN;
        } catch (CommandNotFoundException e) {
            if (!command[0].isEmpty()) {
                consolePrinter.printError("Command " + command[0] + " is not exist. Enter 'help' for helping");
            } else {
                consolePrinter.printInformation("");
            }
        }
        return ProgramStatus.ERROR;
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