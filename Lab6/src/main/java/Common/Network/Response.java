package Common.Network;

import java.io.Serializable;

/**
 * Response, which sent from server to client
 */
public class Response implements Serializable {
    private final String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}