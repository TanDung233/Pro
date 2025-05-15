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
 * Server's sender
 */
public class SvSender {
    private final int PACKET_SIZE;
    private final DatagramChannel dc;
    private final Logger logger;
    private final SocketAddress clientAddr;

    public SvSender(int PACKET_SIZE, DatagramChannel dc, Logger logger, SocketAddress clientAdrr) {
        this.PACKET_SIZE = PACKET_SIZE;
        this.dc = dc;
        this.logger = logger;
        this.clientAddr = clientAdrr;
    }

    /**
     * Send data to client
     * @param data data, which need to send
     */
    public void sendData(byte[] data) {
        int DATA_SIZE = PACKET_SIZE - 1;
        byte[][] ret = new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][DATA_SIZE];

        int start = 0;
        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }

        logger.info("Splitting the response into " + ret.length +" chunk(s) and sending them to the client...");

        for(int i = 0; i < ret.length; i++) {
            var chunk = ret[i];
            try {
                if (i == ret.length - 1) {
                    var lastChunk = Bytes.concat(chunk, new byte[]{1});
                    dc.send(ByteBuffer.wrap(lastChunk), clientAddr);
                    logger.info("Last chunk has been sent to client");
                } else {
                    var tempChunk = Bytes.concat(chunk, new byte[]{0});
                    dc.send(ByteBuffer.wrap(tempChunk), clientAddr);
                    logger.info("Sending chunks...");
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "An error occurred while sending response to the client");
            }

        }

        logger.info("Response sending to client complected.");
    }
}