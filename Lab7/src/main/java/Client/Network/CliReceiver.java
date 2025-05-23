package Client.Network;

import Client.Utility.ConsolePrinter;
import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * client's receiver
 */
public class CliReceiver {
    private final int PACKET_SIZE;
    private final DatagramChannel dc;
    ConsolePrinter consolePrinter = new ConsolePrinter();

    public CliReceiver(int PACKET_SIZE, DatagramChannel dc) throws IOException {
        this.PACKET_SIZE = PACKET_SIZE;
        this.dc = dc;
        dc.configureBlocking(false);
    }

    /**
     * receive data from server
     * @return all received data
     */
    public byte[] receiveData() {
        boolean finished = false;
        byte[] result = new byte[0];

        consolePrinter.printInformation("Receiving response from server...");

        while (!finished) {
            ByteBuffer buffer = ByteBuffer.allocate(PACKET_SIZE);
            SocketAddress serverAddr;

            try {
                serverAddr = dc.receive(buffer);
                if (serverAddr == null) continue;

                buffer.flip();
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);

                if (data.length < 1) {
                    consolePrinter.printInformation("Received empty or invalid packet.");
                    continue;
                }

                byte flag = data[data.length - 1];
                byte[] actualData = Arrays.copyOf(data, data.length - 1);
                result = Bytes.concat(result, actualData);

                if (flag == 1) {
                    finished = true;
                    consolePrinter.printInformation("Final chunk received. Total size: " + result.length + " bytes");
                }

            } catch (IOException e) {
                consolePrinter.printError("Failed to receive response from server" + e);
                return null;
            }
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
                consolePrinter.printError("Client failed to receive data");
                return null;
            }
        }
        buffer.flip();
        return buffer.array();
    }
}