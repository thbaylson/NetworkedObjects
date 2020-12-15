package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * This class represents a concrete implementation of a magic server that uses
 * the TCP transport layer protocol.
 */
public class TcpMagicServer extends AbstractMagicServer {

    /**
     * Creates a new TcpMagicServer that listens for connections on the default
     * magic TCP port, and uses the default card source.
     */
    public TcpMagicServer() throws FileNotFoundException {
        super();
    }

    /**
     * Creates a new TcpMagicServer that listens for connections on the
     * specified port.
     * 
     * @param port The specified port number.
     */
    public TcpMagicServer(int port) throws FileNotFoundException {
        super(port);
    }

    /**
     * Creates a new TcpMagicServer that listens for connections on the default
     * magic TCP port and uses the specified card source.
     * 
     * @param source The specified card source.
     */
    public TcpMagicServer(CardSource source) throws FileNotFoundException {
        super(source);
    }

    /**
     * Creates a new TcpMagicServer that listens for connections on the 
     * specified port and uses the specified card source.
     * 
     * @param port   The specified port number.
     * @param source The specified card source.
     */
    public TcpMagicServer(int port, CardSource source)
        throws FileNotFoundException{
        super(port, source);
    }

    /**
     * {@inheritDoc}
     * @throws MagicServerException If an error occurs while trying to listen
     *  for connections.
     */
    @Override
    public void listen() throws MagicServerException {
        try {
            //Set up the server and socket
            ServerSocket server = new ServerSocket(this.port);
            Socket socket = server.accept();

            InputStreamReader isr = new InputStreamReader(
                socket.getInputStream());

            BufferedReader br = new BufferedReader(isr);

            //Receive flags
            String flagString = br.readLine();
            String[] flags = flagString.split("");

            //Initialize output stream
            OutputStream output = socket.getOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(output);

            //Set flags and send cards
            for(int i = 0; i < 20 * flags.length; i++){
                Random rand = new Random();
                String currentFlag = flags[rand.nextInt(flags.length)];

                setCardsReturned(currentFlag);
                objStream.writeObject(this.source.next());
            }

            //Close resources
            output.close();
            socket.close();
            server.close();
            
        } catch (IOException e) {
            throw new MagicServerException();
        }
    }
}
