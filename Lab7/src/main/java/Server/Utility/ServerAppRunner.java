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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Class of operation of the program
 */
public class ServerAppRunner implements Runnable{
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    private Server server;
    private boolean serverIsRunning = true;
    private final ExecutorService readPool     = Executors.newFixedThreadPool(32);
    private final ExecutorService processPool  = Executors.newCachedThreadPool();
    private final ForkJoinPool sendPool     = new ForkJoinPool();
    private final DatabaseHandler databaseHandler;
    private final ExecutorService connectionPool = Executors.newCachedThreadPool();



    public ServerAppRunner(CollectionManager collectionManager,
                           int port, DatabaseUserManager databaseUserManager,
                           DatabaseCollectionManager databaseCollectionManager,
                           DatabaseHandler databaseHandler) throws IOException {
        this.collectionManager = collectionManager;
        this.commandManager = new CommandManager(collectionManager, databaseUserManager, databaseCollectionManager);
        this.server =  new Server(InetAddress.getLocalHost(), port);
        this.databaseHandler = databaseHandler;
    }

    @Override
    public void run() {
        ServerApp.logger.info("Server is waiting to receive datagrams...");

        connectionPool.submit(new ConnectionHandler(
                server, commandManager, readPool, processPool, sendPool,this));

        while (serverIsRunning) {
            try { Thread.sleep(1000); } catch (InterruptedException e) { break; }
        }
    }


    /**
     * Stop the program
     */
    public synchronized void stop() {
        shutdown(connectionPool);
        shutdown(readPool);
        shutdown(processPool);
        shutdown(sendPool);
        server.disconnectFromClient();
        commandManager.clearHistory();
        serverIsRunning = false;
        server.clearSocket();
        ServerApp.logger.log(Level.INFO, "Server disconnected from client\nThe program was ended.");
    }

    private void shutdown(ExecutorService es) {
        es.shutdown();
        try { es.awaitTermination(5, TimeUnit.SECONDS); }
        catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }
}