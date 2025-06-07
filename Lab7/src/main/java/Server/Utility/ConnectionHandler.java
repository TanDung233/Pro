package Server.Utility;

import Common.Network.ProgramCode;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Memory.CommandManager;
import Server.Network.*;
import Server.ServerApp;
import org.apache.commons.lang.SerializationUtils;

import java.util.concurrent.*;
import java.util.logging.Level;

/**
 * Handles user connection.
 */
public class ConnectionHandler implements Runnable {
    private Server server;
    private CommandManager commandManager;
    private final ExecutorService readPool;
    private final ExecutorService processPool;
    private final ForkJoinPool sendPool;
    private final ServerAppRunner appRunner;


    public ConnectionHandler(Server server, CommandManager commandManager,
                             ExecutorService readPool,
                             ExecutorService processPool,
                             ForkJoinPool sendPool,
                             ServerAppRunner appRunner) {
        this.server = server;
        this.commandManager = commandManager;
        this.readPool = readPool;
        this.processPool = processPool;
        this.sendPool = sendPool;
        this.appRunner = appRunner;
    }

    /**
     * Run the handler
     */
    @Override
    public void run() {
        boolean alive = true;
        while (alive && !Thread.currentThread().isInterrupted()) {
            Response resp = CompletableFuture
                    .supplyAsync(server::receiveData, readPool)

                    .thenApplyAsync(bytes -> {
                        Request requestFromUser = (Request) SerializationUtils.deserialize(bytes);
                        try {
                            ServerApp.logger.info("Request '" + requestFromUser.getNameCommand() + "' processed.");
                            return new RequestHandler(commandManager, requestFromUser).call();
                        } catch (Exception e) {
                            throw new CompletionException(e);
                        }
                    }, processPool)

                    .thenApplyAsync((Response rs) -> {
                                byte[] bytes = SerializationUtils.serialize(rs);
                                server.sendData(bytes);
                                return rs;
                            }, sendPool
                    ).join();
            ProgramCode code = resp.getResponseCode();
            if (code == ProgramCode.CLIENT_EXIT){
                ServerApp.logger.log(Level.INFO, "Server disconnected from client");
                server.disconnectFromClient();
            }
            if (code == ProgramCode.SERVER_EXIT && code == ProgramCode.CLIENT_EXIT) {
                appRunner.stop();
                alive = false;
            }
        }
        ServerApp.logger.info("Connection handler stopped.");
    }
}
