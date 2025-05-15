package Server.Network;

import Common.Network.Request;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server
 */
public class Server {
    private final int PACKET_SIZE = 1024;
    private final Logger logger;
    private final SocketAddress serverAddr;
    private SocketAddress clientAddr;
    private SvSender svSender;
    private SvReceiver svReceiver;
    private DatagramChannel dc;


    public Server(InetAddress hostAddr, int port, Logger log) throws IOException {
        this.serverAddr = new InetSocketAddress(hostAddr, port);
        this.clientAddr = null;
        this.dc = DatagramChannel.open().bind(serverAddr);
        this.logger = log;
        this.svReceiver = new SvReceiver(PACKET_SIZE, dc, logger);
    }


    /**
     * Server connects to client
     *
     * @return client address
     */
    public SocketAddress connectToClient() {
        try {
            dc.connect(clientAddr);
            this.svSender = new SvSender(PACKET_SIZE, dc, logger, clientAddr);
            return clientAddr;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Connection failed!");
        }
        return null;

    }

    /**
     * Server disconnects from client
     */
    public void disconnectFromClient() {
        try {
            dc.disconnect();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Server clears data socket
     */
    public void clearSocket() {
        try {
            dc.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * Server receives data from client
     *
     * @return received data
     */
    public byte[] receiveData() {
        byte[] data = svReceiver.receiveData();
        this.clientAddr = svReceiver.getClientAddr();
        return data;
    }

    public Request receiveRequest() {
        byte[] data = receiveData();
        if (data == null) {
            logger.log(Level.WARNING, "No data received to deserialize.");
            return null;
        }

        try {
            Request request = (Request) SerializationUtils.deserialize(data);
            logger.log(Level.INFO, "Received request: " + request.toString());
            return request;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to deserialize Request!", e);
            return null;
        }
    }

    public SocketAddress getClientAddr() {
        return clientAddr;
    }

    /**
     * Server sends data to client
     *
     * @param data data which need to send
     */
    public void sendData(byte[] data) {
        svSender.sendData(data);
    }

    /**
     * Server connects to client
     *
     * @return if server is connected
     */
    public boolean serverIsConnecting() {
        return dc.isConnected();
    }
}
