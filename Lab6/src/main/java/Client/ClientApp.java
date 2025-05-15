package Client;


import Client.Network.Client;
import Client.Utility.CliRunner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class
 */
public class ClientApp {
    private static final int PORT = 1234; //server port
    public static final Logger logger = Logger.getLogger("Client_Logger");


    public static void main( String[] args){
        try {
            Client client = new Client(InetAddress.getLocalHost(), PORT, logger);
            CliRunner app = new CliRunner(client, logger);
            app.run();
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Host unknown!", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to connect to the server!", e);
        }
    }
} 