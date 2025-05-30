package Server;

import Server.Manager.Memory.CollectionManager;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.Manager.Database.DatabaseUserManager;
import Server.Utility.DatabaseHandler;
import Server.Utility.ServerAppRunner;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of Server
 */
public class ServerApp {
    public static Logger logger = Logger.getLogger("ServerLogger");
    private static int port = 2345;
    //private static String databaseUsername = "postgres";
    private static String databaseUsername = "s463227";
    private static String databaseHost = "pg";
    //private static String databasePassword = "230304";
    private static String databasePassword = "CAIgpjcssdDf2AoC";

    private static String databaseAddress = "jdbc:postgresql://pg:5432/studs";

    public static void main(String[] args){
        try{
            DatabaseHandler databaseHandler = new DatabaseHandler(databaseAddress, databaseUsername, databasePassword);
            DatabaseUserManager databaseUserManager = new DatabaseUserManager(databaseHandler);
            DatabaseCollectionManager databaseCollectionManager = new DatabaseCollectionManager(databaseUserManager, databaseHandler);
            CollectionManager collectionManager = new CollectionManager(databaseCollectionManager);

            ServerAppRunner app = new ServerAppRunner(collectionManager, port, databaseUserManager, databaseCollectionManager);
            app.run();
        } catch (IOException e) {
            ServerApp.logger.log(Level.WARNING, e.toString());
        }

    }

}