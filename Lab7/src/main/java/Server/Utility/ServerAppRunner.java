package Server.Utility;


import Server.Manager.Memory.CommandManager;
import Server.Manager.Memory.CollectionManager;
import Server.ServerApp;
import Server.Network.Server;
import Server.Manager.Database.DatabaseCollectionManager;
import Server.Manager.Database.DatabaseUserManager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Class of operation of the program
 */
public class ServerAppRunner implements Runnable{
    private final CommandManager commandManager;
    private final DatabaseUserManager databaseUserManager;
    private final DatabaseCollectionManager databaseCollectionManager;
    private final CollectionManager collectionManager;
    private Server server;
    private boolean serverIsRunning = true;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);



    public ServerAppRunner(CollectionManager collectionManager, int port, DatabaseUserManager databaseUserManager, DatabaseCollectionManager databaseCollectionManager) throws IOException {
        this.collectionManager = collectionManager;
        this.databaseUserManager = databaseUserManager;
        this.databaseCollectionManager = databaseCollectionManager;
        this.commandManager = new CommandManager(collectionManager, databaseUserManager, databaseCollectionManager);
        this.server =  new Server(InetAddress.getLocalHost(), port);
    }

    /**
     * Run the program
     */
    @Override
    public void run() {
        ServerApp.logger.log(Level.INFO, "Server is waiting to connect...");
        while (serverIsRunning) {
            fixedThreadPool.submit(new ConnectionHandler(server, commandManager));
            server.disconnectFromClient();
        }
        try {
            fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            ServerApp.logger.log(Level.WARNING, e.toString());
        }
        stop();
    }

    /**
     * Stop the program
     */
    private synchronized void stop() {
        fixedThreadPool.shutdown();
        collectionManager.saveCollection();
        server.disconnectFromClient();
        commandManager.clearHistory();
        serverIsRunning = false;
        server.clearSocket();
        ServerApp.logger.log(Level.INFO, "Server disconnected from client\nThe program was ended.");
    }



}