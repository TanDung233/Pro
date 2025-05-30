package Client.Network;

import Client.Utility.ConsolePrinter;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client's sender
 */
public class CliSender {
    private final int DATA_SIZE;
    private final DatagramChannel dc;
    private final SocketAddress serverAddr;
    ConsolePrinter consolePrinter = new ConsolePrinter();

    public CliSender(int PACKET_SIZE, DatagramChannel dc, SocketAddress addr) throws IOException {
        this.DATA_SIZE = PACKET_SIZE - 1;
        this.dc = dc;
        dc.configureBlocking(false);
        this.serverAddr = addr;
    }

    /**
     * send data to server
     * @param data data
     */
    public void sendData(byte[] data) {
        int number_chunk = (int)Math.ceil((double) data.length /DATA_SIZE);
        byte[][] chunk = new byte[number_chunk][DATA_SIZE];

        int start = 0;
        for(int i = 0; i < number_chunk; i++) {
            chunk[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }

         consolePrinter.printInformation("Splitting the request into " + number_chunk +" chunk(s) and sending them to the server...");

        int PACKET_SIZE = DATA_SIZE + 1;
        for (int i = 0; i < number_chunk; i++){
            byte[] tempChunk = chunk[i];
            if (i == number_chunk - 1){
                ByteBuffer buffer = ByteBuffer.allocate(PACKET_SIZE);
                buffer.put(tempChunk);
                buffer.put(new byte[]{1});
                byte[] lastChunk = buffer.array();

                try {
                    dc.send(ByteBuffer.wrap(lastChunk), serverAddr);
                    Thread.sleep(500);
                    consolePrinter.printInformation("Last chunk has been sent to server.");
                } catch (IOException | InterruptedException e){
                    consolePrinter.printError("Failure to receive response from Server.");
                }

            } else {
                ByteBuffer buffer = ByteBuffer.allocate(PACKET_SIZE);
                buffer.put(tempChunk);
                buffer.put(new byte[]{0});
                byte[] sentChunk = buffer.array();

                try {
                    dc.send(ByteBuffer.wrap(sentChunk), serverAddr);   //exception
                    consolePrinter.printInformation("A chunk with length " + sentChunk.length + " has been sent to server." );
                } catch (IOException e){
                    consolePrinter.printError("Failure to receive response from Server.");
                }

            }
        }

        consolePrinter.printInformation("Request sending completed.");
    }
}