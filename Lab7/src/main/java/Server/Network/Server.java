package Server.Network;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.*;
import java.util.logging.Level;
import Server.ServerApp;

/**
 * Server
 */
public class Server {
    private final int PACKET_SIZE = 2048*2;
    private final SocketAddress serverAddr;
    private SocketAddress clientAddr;
    private ServerSender svSender;
    private ServerReceiver svReceiver;
    private DatagramSocket ds;

    public Server(InetAddress hostAddr, int port) throws IOException {
        this.serverAddr = new InetSocketAddress(hostAddr, port);
        this.clientAddr = null;
        this.ds = new DatagramSocket(port,hostAddr);
        this.svReceiver = new ServerReceiver(PACKET_SIZE, ds);
    }

    /**
     * Server clears data socket
     */
    public void clearSocket() {
        ds.close();
    }

    /**
     * Server disconnects from client
     */
    public void disconnectFromClient() {
        ds.disconnect();
    }
    /**
     * Server receives data from client
     *
     * @return received data
     */
    public byte[] receiveData() {
        byte[] data = svReceiver.receiveData();
        this.clientAddr = svReceiver.getClientAddr();
        this.svSender = new ServerSender(PACKET_SIZE, ds, clientAddr);
        return data;
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
        return ds.isConnected();
    }
}
