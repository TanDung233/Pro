package Common.Network;

import java.io.Serializable;

/**
 * Response, which sent from server to client
 */
public class Response implements Serializable {
    private final String message;
    private final ProgramCode programCode;

    public Response(String message) {
        this.message = message;
        this.programCode = ProgramCode.OK;
    }

    public Response(String message, ProgramCode programCode) {
        this.message = message;
        this.programCode = programCode;
    }

    public String getMessage() {
        return message;
    }

    public ProgramCode getResponseCode() {
        return programCode;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}