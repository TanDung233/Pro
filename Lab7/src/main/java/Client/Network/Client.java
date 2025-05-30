package Client.Network;
import Common.Network.Request;
import Common.Network.Response;
import Client.Utility.ConsolePrinter;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;


/**
 * Client
 */
public class Client {
    private final int PACKET_SIZE = 1024*4;

    private final SocketAddress serverAddr;
    private DatagramChannel dc;

    private final CliSender cliSender;
    private final CliReceiver cliReceiver;

    private int reconnectionAttempts = 5;
    ConsolePrinter consolePrinter = new ConsolePrinter();

    public Client(InetAddress hostAddress, int port) throws IOException {
        this.serverAddr = new InetSocketAddress(hostAddress, port);
        connectToServer();
        this.cliSender = new CliSender(PACKET_SIZE, dc, serverAddr);
        this.cliReceiver = new CliReceiver(PACKET_SIZE, dc);
    }

    /**
     * send and receive request (command)
     * @param request command to server
     * @return response from server
     */
    public Response sendAndReceiveCommand(Request request) {
        byte[] data = SerializationUtils.serialize(request);
        byte[] responseBytes = sendAndReceiveData(data);
        if (responseBytes == null) return null;

        Response response = (Response) SerializationUtils.deserialize(responseBytes);

        return response;
    }

    /**
     * send and receive byte data to and from server
     * @param data sent byte data
     * @return received byte data
     */
    public byte[] sendAndReceiveData(byte[] data)  {
        cliSender.sendData(data);
        return cliReceiver.receiveData();
    }

    /**
     * Check and notice if DatagramChannel is connected
     */
    public void connectToServer(){
        consolePrinter.printInformation( "DatagramChannel is connecting to the server...");
        do {
            try{
                dc = DatagramChannel.open().bind(null).connect(serverAddr);
                dc.configureBlocking(false);

                if (dc.isConnected()){
                    consolePrinter.printInformation("DataChannel connected to " + serverAddr);
                    break;
                }

                reconnectionAttempts -= 1;
                if (reconnectionAttempts >= 1)  consolePrinter.printError( "Connection failed. Reconnecting to the server...");
            } catch (IOException e) {
                consolePrinter.printError("An error occurred while connecting to the server!" + e);
            }
        } while (reconnectionAttempts != 0);
        if (reconnectionAttempts == 0 ) consolePrinter.printInformation("The connection attempt has expired!");
    }

    /**
     * Disconnect to server
     */
    public void disconnectToServer() {
        consolePrinter.printInformation( "DatagramChannel disconnected to Server");
        try {
            dc.disconnect();
        } catch (IOException e) {
            consolePrinter.printError("Could not disconnect to Server");
        }
    }
}