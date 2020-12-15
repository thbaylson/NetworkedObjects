package client;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import common.Card;

public class MagicUdpClient extends AbstractMagicClient {

    // The length of our UDP buffer
    private static final int UDP_BUFFER = 256;

    /**
     * Initializes a new MagicTcpClient with the specified host, the
     * default port, and the default flags
     * @param host The address of the remote host to which to connect.
     */
    public MagicUdpClient(InetAddress host) {
        super(host);
    }

    /**
     * Initializes a new MagicTcpClient with the specified host, the
     * specified port, and the default flags
     * @param host The address of the remote host to which to connect.
     * @param port The port on the remote host to which to connect.
     */
    public MagicUdpClient(InetAddress host, int port) {
        super(host, port);
    }

    /**
     * Initializes a new MagicTcpClient with the specified host, port,
     * and flags
     * @param host The address of the remote host to which to connect.
     * @param port The port on the remote host to which to connect.
     * @param flag The arguments to send to the server.
     */
    public MagicUdpClient(InetAddress host, int port, String flags) {
        super(host, port, flags);
    }

    /**
     * Establishes a UDP connection to the host/port specified when this object
     * was created, reads a continuous stream of random cards from the socket's
     * input stream, and prints that data to the specified output stream.
     * @param out The stream to which to write the random cards received.
     */
    @Override
    public void printToStream(PrintStream out){
        try{
            //Establish UDP Connection and send flags
            byte[] buffer = this.flag.getBytes();

            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buffer, 
                buffer.length, this.host, this.port);

            socket.send(packet);

            //Prepare to receive a Card from the server
            buffer = new byte[UDP_BUFFER];
            packet = new DatagramPacket(buffer, UDP_BUFFER);

            //Receive as many Cards as we need to and print them
            for(int i = 0; i < 20 * this.flag.length(); i++){
                socket.receive(packet);
                ByteArrayInputStream cardBytes = new ByteArrayInputStream(
                    packet.getData());
                
                ObjectInputStream objStream = new ObjectInputStream(cardBytes);
                Card card = (Card) objStream.readObject();
                out.println(card.toString());
                
                //Close loop resources in the last interation
                if(i == 20 * this.flag.length()){
                    objStream.close();
                    cardBytes.close();
                }
            }

            //Close resources
            socket.close();
        }catch(EOFException eofe){
            System.out.println("An error has occured during communication");
        }catch(IOException ioe){
            System.out.println("An error has occured during communication");
        }catch(ClassNotFoundException cnfe){
            System.out.println("An error has occured during communication");
        }
    }
}
