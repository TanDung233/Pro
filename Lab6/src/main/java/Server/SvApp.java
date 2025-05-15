package Server;

import Server.Manager.CollectionManager;
import Server.Network.Server;
import Server.Utility.Csv.CSVProcess;
import Server.Utility.SvRunner;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class
 */
public class SvApp {
    public static final int PORT = 1234;
    public static Logger logger = Logger.getLogger("ServerLogger");


    public static void main(String[] args){
        try {
            if (args.length == 0) {
                System.out.println("Enter the name of the downloaded file as a command line argument");
                System.exit(1);
            }

            CSVProcess csvProcess = new CSVProcess();
            CollectionManager collectionManager= new CollectionManager(csvProcess);
            collectionManager.getCollectionFromFile(args[0]);
            Server server = new Server(InetAddress.getLocalHost(), PORT, logger);
            SvRunner appRunner = new SvRunner(collectionManager, server, logger);

            appRunner.run();
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Unknown host", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}