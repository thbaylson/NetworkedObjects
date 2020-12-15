package server;

import java.io.FileNotFoundException;

/**
 * This class contains an application that can drive both the TCP and UDP
 * implementations of a MagicServer.
 */
public class MagicServerDriver {

    public MagicServerDriver() {}

    /**
     * This method serves as the entry point of the program.
     * 
     * @param args Command line arguments to the program. There must be exactly 1 or
     *             2 arguments. The first parameter specifies the protocol. The
     *             second parameter, if present, must be the port number on which
     *             the server will listen for requests.
     */
    public static void main(String[] args) {
        try {
            //Verify length of command line arguments
            boolean goodLength = (0 < args.length && args.length <= 2);

            //Verify protocol
            String protoc = (goodLength) ? args[0].toLowerCase() : "";
            boolean goodProtoc = protoc.equals("tcp") || protoc.equals("udp");

            //Verify port. The range of ports used by programs
            //is 1024-49151 according to tcp-udp-ports.com
            int port = (args.length == 2) 
                ? Integer.parseInt(args[1]) : AbstractMagicServer.DEFAULT_PORT;
            boolean goodPort = 1024 <= port && port <= 49151;

            //Initialize source and server
            CardSource source = new CardSource();
            AbstractMagicServer server;
            if(goodLength && goodProtoc && goodPort){
                if(protoc.equals("tcp")){
                    server = new TcpMagicServer(port, source);
                }else{
                    server = new UdpMagicServer(port, source);
                }
                
                //Keep the server listening for incoming connections
                while(true){
                    server.listen();                    
                }
            }else{
                System.out.println("Usage:\n\t" + 
                    "java server.MagicServerDriver tcp|udp");
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Could not locate cards.csv");
        } catch (MagicServerException mse){
            System.out.println("An error has occured during communication");
        }
    }
}
