package Server.Network;

import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server's receiver
 */
public class SvReceiver {
    private final int PACKET_SIZE;
    private final DatagramChannel dc;
    private final Logger logger;
    private SocketAddress clientAddr = null;

    public SvReceiver(int PACKET_SIZE, DatagramChannel dc, Logger logger) {
        this.PACKET_SIZE = PACKET_SIZE;
        this.dc = dc;
        this.logger = logger;
    }

    /**
     * Receive data
     * @return received data
     */
    public byte[] receiveData()  {
        boolean received = false;
        var result = new byte[0];
        SocketAddress addr = null;

        while(!received) {
            try {

                var buf = ByteBuffer.allocate(PACKET_SIZE);
                clientAddr = dc.receive(buf);
                if (clientAddr == null) throw new IOException();
                byte[] data = buf.array();

                logger.log(Level.INFO, "Server successfully connected to the client " + clientAddr);
                logger.info("Getting request from client" + clientAddr);

                if (data[data.length - 1] == 1) {
                    received = true;
                    logger.info("Request getting from client was done");
                }
                result = Bytes.concat(result, Arrays.copyOf(data, data.length - 1));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "An error occurred while receiving the request");
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
