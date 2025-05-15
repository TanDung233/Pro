package Common.Network;

import Common.Data.Person;
import java.io.Serializable;

/**
 * Request, which sent from client to server
 */

public class Request implements Serializable {
    private final String nameCommand;
    private Object parameter = null;
    private Person updatedPerson = null;  //only update_id command use him

    /**
     * Make request for command without parameters (command without person creation)
     * @param nameCommand name command
     */
    public Request(String nameCommand) {
        this.nameCommand = nameCommand;
    }

    /**
     * Make request for command without parameters (command with person creation)
     * @param nameCommand name command
     * @param createdPerson created person by user or script
     */
    public Request(String nameCommand, Person createdPerson) {
        this.nameCommand = nameCommand;
        this.parameter = createdPerson;
    }

    /**
     * Make request for command with parameter is person name
     * @param nameCommand command name
     * @param namePerson person name
     */
    public Request(String nameCommand, String namePerson) {
        this.nameCommand = nameCommand;
        this.parameter = namePerson;
    }

    /**
     * Make request for command with parameter is person id
     * @param nameCommand command name
     * @param idPerson person id
     */
    public Request(String nameCommand, Integer idPerson) {
        this.nameCommand = nameCommand;
        this.parameter = idPerson;
    }

    /**
     * Make request for update_id command
     * @param nameCommand command name
     * @param idPerson person id
     */
    public Request(String nameCommand, Integer idPerson, Person updatedPerson) {
        this.nameCommand = nameCommand;
        this.parameter = idPerson;
        this.updatedPerson = updatedPerson;
    }

    public String getNameCommand() {
        return nameCommand;
    }

    public Object getArgumentCommand() {
        return parameter;
    }

    public Person getUpdatedPerson() {
        return updatedPerson;
    }

    /**
     *
     * @return information of the request
     */
    @Override
    public String toString() {
        return "Request \"" + nameCommand + "[" + (parameter != null ? parameter.toString() : " ") + "]["
                + (updatedPerson != null ? updatedPerson.toString() : " ") + "]\"";
    }
}

