package Client.Network;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * client's receiver
 */
public class CliReceiver {
    private final int PACKET_SIZE;
    private final DatagramChannel dc;
    private final Logger logger;

    public CliReceiver(int PACKET_SIZE, DatagramChannel dc, Logger logger) {
        this.PACKET_SIZE = PACKET_SIZE;
        this.dc = dc;
        this.logger = logger;
    }

    /**
     * receive data from server
     * @return all received data
     */
    public byte[] receiveData() {
        boolean dataIsReceived = false;
        byte[] result = new byte[0];

        while(!dataIsReceived) {
            byte[] data = receivedData(PACKET_SIZE);  //exception
            if (data == null) return null;
            logger.log(Level.INFO, "Receiving response from server..." );


            if (data[data.length - 1] == 1) {
                dataIsReceived = true;
                logger.log(Level.INFO, "Response getting from server completed.");
            }
            ByteBuffer buffer = ByteBuffer.allocate(result.length + data.length);
            buffer.put(result);
            buffer.put(data);
            result = buffer.array();
        }

        return result;
    }

    /**
     * receive data chunks from server
     * @return chunk of received data
     */
    private byte[] receivedData(int bufferSize)  {
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress serverAddress = null;
        while (serverAddress == null) {
            try {
                serverAddress = dc.receive(buffer);  //exception
            } catch (IOException e) {
                logger.log(Level.WARNING, "Client failed to receive data");
                return null;
            }
        }
        buffer.flip();
        return buffer.array();
    }
}