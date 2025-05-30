package Server.Utility;

import Common.Network.ProgramCode;
import Common.Network.Request;
import Common.Network.Response;
import Server.Manager.Memory.CommandManager;
import Server.Network.*;
import Server.ServerApp;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.logging.Level;

/**
 * Handles user connection.
 */
public class ConnectionHandler implements Runnable {
    private Server server;
    private CommandManager commandManager;

    public ConnectionHandler(Server server, CommandManager commandManager) {
        this.server = server;
        this.commandManager = commandManager;
    }

    /**
     * Run the handler
     */
    @Override
    public void run() {
        ExecutorService fixedPool = Executors.newFixedThreadPool(1);
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        ForkJoinPool forkPool = ForkJoinPool.commonPool();

        Request requestFromUser;
        Response responseToUser = null;

        do {
            try {
                Future<byte[]> dataFuture = fixedPool.submit(server::receiveData);
                byte[] dataFromUser = dataFuture.get();

                requestFromUser = (Request) SerializationUtils.deserialize(dataFromUser);

                Future<Response> responseFuture = cachedPool.submit(
                        new RequestHandler(commandManager, requestFromUser));
                responseToUser = responseFuture.get();
                ServerApp.logger.info("Request '" + requestFromUser.getNameCommand() + "' processed.");

                Response finalResponseToUser = responseToUser;
                byte[] dataToUser = SerializationUtils.serialize(finalResponseToUser);

                forkPool.submit(() -> {
                    server.sendData(dataToUser);
                }).get();

            } catch (InterruptedException | ExecutionException e) {
                ServerApp.logger.log(Level.WARNING, e.toString());
                
            }

        } while (responseToUser.getResponseCode() != ProgramCode.SERVER_EXIT &&
                responseToUser.getResponseCode() != ProgramCode.CLIENT_EXIT);

        fixedPool.shutdown();
        cachedPool.shutdown();
        ServerApp.logger.log(Level.INFO, "Client disconnect to server.");
    }
}