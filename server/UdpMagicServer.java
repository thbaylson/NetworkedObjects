package server;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class UdpMagicServer extends AbstractMagicServer {

    /**The length of the UDP buffer */
    private static final int UDP_BUFFER_LENGTH = 256;
    
    /**
     * Creates a new UdpMagicServer that listens for connections on the default
     * magic UDP port, and uses the default card source.
     */
    public UdpMagicServer() throws FileNotFoundException {
        super();
    }

    /**
     * Creates a new UdpMagicServer that listens for connections on the 
     * specified port.
     * @param port The specified port number.
     */
    public UdpMagicServer(int port) throws FileNotFoundException {
        super(port);
    }

    /**
     * Creates a new UdpMagicServer that listens for connections on the default
     * magic UDP port and uses the specified card source.
     * @param source The specified card source.
     */
    public UdpMagicServer(CardSource source) throws FileNotFoundException {
        super(source);
    }

    /**
     * Creates a new UdpMagicServer that listens for connections on the
     * specified port and uses the specified card source.
     * @param port The specified port number.
     * @param source The specified card source.
     */
    public UdpMagicServer(int port, CardSource source) 
        throws FileNotFoundException {
        super(port, source);
    }

    /**
     * {@inheritDoc}
     * @throws MagicServerException If an error occurs while trying to listen 
     *  for connections.
     */
    @Override
    public void listen() throws MagicServerException{
        try{
            //Set up the UDP socket
            byte[] buffer = new byte[UDP_BUFFER_LENGTH];
            DatagramSocket server = new DatagramSocket(this.port);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            //Receive packet
            server.receive(packet);
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();

            //Unpack flags
            String fStr = new String(packet.getData(), 0, packet.getLength());
            String[] flags = fStr.split("");

            for(int i = 0; i < 20 * flags.length; i++){
                //Initialize output stream and set flags
                ByteArrayOutputStream cardBytes 
                    = new ByteArrayOutputStream(UDP_BUFFER_LENGTH);
                ObjectOutputStream objStream 
                    = new ObjectOutputStream(cardBytes);
                
                Random rand = new Random();
                String currentFlag = flags[rand.nextInt(flags.length)];
                setCardsReturned(currentFlag);

                //Send the card
                objStream.writeObject(this.source.next());
                buffer = cardBytes.toByteArray();
                packet = new DatagramPacket(buffer, buffer.length,
                    clientAddress, clientPort);
                
                server.close();
                server = new DatagramSocket();
                server.send(packet);

                objStream.close();
                cardBytes.close();
            }

            //Close resources
            server.close();

        } catch (IOException e) {
            throw new MagicServerException();
        }

    }
}
