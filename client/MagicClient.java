package client;

import java.io.PrintStream;

/**
 * The interface to a magic client component.
 */
public interface MagicClient {
    
    /**
     * Continually reads a continuous stream of random cards from the socket
     * and prints that data to the specified output stream.
     * @param out The stream to which to write the random cards received.
     */
    void printToStream(PrintStream out) throws java.io.IOException;
}
