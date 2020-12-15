package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import common.Card;

/**
 * This class represents a concrete implementation of a magic client that uses
 * the TCP network layer protocol
 */
public class MagicTcpClient extends AbstractMagicClient {

    /**
     * Initializes a new MagicTcpClient with the specified host, the default 
     * port, and the default flags
     * 
     * @param host The address of the remote host to which to connect.
     */
    public MagicTcpClient(InetAddress host) throws IOException {
        super(host);
    }

    /**
     * Initializes a new MagicTcpClient with the specified host, the specified 
     * port, and the default flags
     * 
     * @param host The address of the remote host to which to connect.
     * @param port The port on the remote host to which to connect.
     */
    public MagicTcpClient(InetAddress host, int port) throws IOException {
        super(host, port);
    }

    /**
     * Initializes a new MagicTcpClient with the specified host, port, and
     * flags
     * 
     * @param host The address of the remote host to which to connect.
     * @param port The port on the remote host to which to connect.
     * @param flag The arguments to send to the server.
     * @throws IOException
     */
    public MagicTcpClient(InetAddress host, int port, String flags) 
    throws IOException{
        super(host, port, flags);
    }

    /**
     * Establishes a TCP connection to the host/port specified when this object
     * was created, reads a continuous stream of random cards from the socket's
     * input stream, and prints that data to the specified output stream.
     * @param out The stream to which to write the random cards received.
     */
    @Override
    public void printToStream(PrintStream out){
        try {
            //Establich TCP connection
            Socket socket = new Socket(this.host, this.port);

            //Send flags
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(this.flag);

            //Prepare to receive a Card from the server
            InputStream input = socket.getInputStream();
            ObjectInputStream objStream = new ObjectInputStream(input);

            // Receive as many Cards as we need to and print them
            for(int i = 0; i < 20 * this.flag.length(); i++){
                Card card = (Card) objStream.readObject();
                out.println(card.toString());
            }

            //Close resources
            objStream.close();
            input.close();
            writer.close();
            socket.close();

        } catch(EOFException eofe){
            System.out.println("An error has occured during communication");
        } catch (IOException ioe) {
            System.out.println("An error has occured during communication");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("An error has occured during communication");
        }
    }
}
