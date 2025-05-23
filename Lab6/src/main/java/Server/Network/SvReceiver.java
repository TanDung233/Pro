package Server.Network;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server's receiver
 */
public class SvReceiver {
    private final int PACKET_SIZE;
    private final DatagramSocket ds;
    private final Logger logger;
    private SocketAddress clientAddr = null;

    public SvReceiver(int PACKET_SIZE, DatagramSocket ds, Logger logger) {
        this.PACKET_SIZE = PACKET_SIZE;
        this.ds = ds;
        this.logger = logger;
    }

    /**
     * Receive data
     * @return received data
     */
    public byte[] receiveData()  {
        boolean received = false;
        byte[] result = new byte[0];

        while(!received){
            byte[] buffer = new byte[PACKET_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                ds.receive(packet);
                clientAddr = packet.getSocketAddress();

                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
                logger.log(Level.INFO, "Server successfully connected to the client " + clientAddr);

                if (data[data.length - 1] == 1) {
                    received = true;
                    logger.info("Request getting from client was done");
                }

                result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));

                } catch (IOException e) {
                logger.log(Level.SEVERE, "An error occurred while receiving the request");
                return null;
            }
        }
        return result;
    }

    /**
     * Get client address
     * @return client's address
     */
    public SocketAddress getClientAddr() {
        return clientAddr;
    }
}
