package Common.Network;

import Common.Data.Person.Person;
import Common.Data.User;

import java.io.Serializable;

/**
 * Request, which sent from client to server
 */

public class Request implements Serializable {
    private final String nameCommand;
    private Object parameter = null;
    private Person updatedPerson = null;  //only update_id command use him
    private User user;

    /**
     * Make request for command without parameters (command without dragon creation)
     * @param nameCommand name command
     */
    public Request(String nameCommand, User user) {
        this.nameCommand = nameCommand;
        this.user = user;
    }


    /**
     * Make request for command without parameters (command with person creation)
     * @param nameCommand name command
     * @param createdPerson created person by user or script
     */
    public Request(String nameCommand, Person createdPerson, User user) {
        this.nameCommand = nameCommand;
        this.parameter = createdPerson;
        this.user = user;
    }

    /**
     * Make request for command with parameter is person name
     * @param nameCommand command name
     * @param namePerson person name
     */
    public Request(String nameCommand, String namePerson, User user) {
        this.nameCommand = nameCommand;
        this.parameter = namePerson;
        this.user = user;
    }

    /**
     * Make request for command with parameter is person id
     * @param nameCommand command name
     * @param idPerson person id
     */
    public Request(String nameCommand, Integer idPerson, User user) {
        this.nameCommand = nameCommand;
        this.parameter = idPerson;
        this.user = user;
    }

    /**
     * Make request for update_id command
     * @param nameCommand command name
     * @param idPerson person id
     */
    public Request(String nameCommand, Integer idPerson, Person updatedPerson, User user) {
        this.nameCommand = nameCommand;
        this.parameter = idPerson;
        this.updatedPerson = updatedPerson;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    /**
     *
     * @return information of the request
     */
    @Override
    public String toString() {
        return "Request \"" + nameCommand + "[" + (parameter != null ? parameter.toString() : " ") + "]["
                + (updatedPerson != null ? updatedPerson.toString():"empty") + "]\" from " + user;
    }
}

