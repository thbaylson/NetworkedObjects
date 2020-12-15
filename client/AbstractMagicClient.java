package client;

import java.io.PrintStream;
import java.net.InetAddress;

/**An abstract class that contains fields and methods that may be common to
 * implementations of the 'magic' protocol. */
public abstract class AbstractMagicClient implements MagicClient{
    
    /**The default card category if no category is supplied. */
    public static final String DEFAULT_FLAG = "A";

    /**The default port on which a magic server listens. */
    public static final int DEFAULT_PORT = 5500;

    /**The address of the remote host to which to connect. */
    protected InetAddress host;
    
    /**The port on the remote host to which to connect. */
    protected int port;
    
    /**The flags which determine which cards to send back. */
    protected String flag;

    /**
     * Initializes a new AbstractMagicClient with the specific host, the
     * default port, and the default flag.
     * @param host The address of the remote host to which to connect.
     */
    public AbstractMagicClient(InetAddress host){
        this(host, DEFAULT_PORT, DEFAULT_FLAG);
    }

    /**
     * Initializes a new AbstractMagicClient with the specified host, the
     * specified port, and the default flag.
     * @param host The address of the remote host to which to connect.
     * @param port The port on the remote host to which to connect.
     */
    public AbstractMagicClient(InetAddress host, int port){
        this(host, port, DEFAULT_FLAG);
    }

    /**
     * Initializes a new AbstractMagicClient with the specified host, port,
     * and flag.
     * @param host The address of the remote host to which to connect.
     * @param port The port on the remote host to which to connect.
     * @param flag The flags which determine which cards to send back.
     */
    public AbstractMagicClient(InetAddress host, int port, String flag){
        this.host = host;
        this.port = port;
        this.flag = flag;
    }

    /**
     * Retruns the address of the host to which to connect.
     * @return The address of the host to which to connect.
     */
    protected InetAddress getHost(){
        return this.host;
    }
    
    /**
     * Returns the port on which the remost host is listening.
     * @return The port on which the remost host is listening.
     */
    protected int getPort(){
        return this.port;
    }
    
    /**
     * Returns the flags that we want to send to the server.
     * @return The flags to send to the server.
     */
    protected String getFlag(){
        return this.flag;
    }

    /**
     * {@inheritDoc}
     * @param out The stream to which to write the random cards received.
     */
    @Override
    public abstract void printToStream(PrintStream out);
}
