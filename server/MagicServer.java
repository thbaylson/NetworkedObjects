package server;

/**
 * The interface to a magic server.
 */
public interface MagicServer {

    /**
     * Causes the magic server to listen for requests.
     * @throws MagicServerException If an error occurs while trying to listen
     *  for connections.
     */
    public void listen() throws MagicServerException;
}
