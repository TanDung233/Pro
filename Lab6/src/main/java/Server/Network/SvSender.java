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
 * Server's sender
 */
public class SvSender {
    private final int PACKET_SIZE;
    private final DatagramSocket ds;
    private final Logger logger;
    private final SocketAddress clientAddr;

    public SvSender(int PACKET_SIZE, DatagramSocket ds, Logger logger, SocketAddress clientAdrr) {
        this.PACKET_SIZE = PACKET_SIZE;
        this.ds = ds;
        this.logger = logger;
        this.clientAddr = clientAdrr;
    }

    /**
     * Send data to client
     * @param data data, which need to send
     */
    public void sendData(byte[] data) {
        int DATA_SIZE = PACKET_SIZE - 1;
        int chunkCount = (int) Math.ceil(data.length / (double) DATA_SIZE);
        byte[][] chunks = new byte[chunkCount][DATA_SIZE];

        // Chia mảng thành từng khối nhỏ
        int start = 0;
        for (int i = 0; i < chunkCount; i++) {
            chunks[i] = Arrays.copyOfRange(data, start, Math.min(start + DATA_SIZE, data.length));
            start += DATA_SIZE;
        }

        logger.info("Splitting the response into " + chunkCount + " chunk(s) and sending them to the client...");

        for (int i = 0; i < chunkCount; i++) {
            byte[] chunkWithFlag;
            if (i == chunkCount - 1) {
                chunkWithFlag = Bytes.concat(chunks[i], new byte[]{1});
            } else {
                chunkWithFlag = Bytes.concat(chunks[i], new byte[]{0});
            }

            DatagramPacket packet = new DatagramPacket(chunkWithFlag, chunkWithFlag.length, clientAddr);

            try {
                ds.send(packet);
                logger.info((i == chunkCount - 1 ? "Last" : "Chunk") + " sent to client: size = " + chunkWithFlag.length);
            } catch (IOException e) {
                logger.log(Level.WARNING, "Fail to client", e);
            }
        }

        logger.info("Response sending to client completed.");
    }
}
