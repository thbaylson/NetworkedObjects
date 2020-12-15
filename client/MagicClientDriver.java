package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class contains an application that can drive both the TCP and UDP
 * implementations of a MagicClient.
 */
public class MagicClientDriver {

    private static final String usage = "Usage:\n\t" + 
        "java client.MagicClientDriver tcp|udp address port|-flags";

    /**
     * Entry point for the application.
     * @param args Command line arguments of the form
     *  tcp|udp hostAddress port|flag flag
     */
    public static void main(String[] args) {
        //Verify length of command line arguments
        boolean goodLength = (2 <= args.length && args.length <= 4);

        //Verify protocol
        String protocol = goodLength ? args[0].toLowerCase() : "";
        boolean goodProtocol= protocol.equals("tcp") || protocol.equals("udp");

        /**
         * Verify the third argument. The third argument can be either a port
         * number or a flag string. The range of ports used by programs
         * is 1024-49151 according to tcp-udp-ports.com, so a valid port is
         * always 4 or 5 digits. The length of the flag string is always
         * between 2 and 3 characters.
        */
        boolean hasThirdArgument = (args.length > 2);
        boolean thirdIsPort = (hasThirdArgument && (args[2].length() > 3));
        int port = AbstractMagicClient.DEFAULT_PORT;
        try{
            port = (thirdIsPort) ? Integer.parseInt(args[2]) : port;
        }catch(NumberFormatException nfe){
            System.out.println(usage);
            System.exit(0);
        }        
        boolean goodPort = (1024 <= port && port <= 49151);

        /**
         * The third position is a flag if there exists a third position and
         * it is not a port. The fourth position is a flag if there exists a
         * fourth position. There's probably a better way to do this, but
         * boolean statements are cheap and this only happens once.
         */
        boolean thirdIsFlag = (hasThirdArgument && !thirdIsPort);
        boolean fourthIsFlag = (args.length > 3);
        boolean hasFlag = thirdIsFlag || fourthIsFlag;
        
        int flagPos = (thirdIsFlag) ? 2 : 3;
        boolean goodFlagSyntax = hasFlag && 
            args[flagPos].substring(0,1).equals("-");
        String flag = hasFlag ? args[flagPos].substring(1) :
            AbstractMagicClient.DEFAULT_FLAG;
        
        flag = (flag.toLowerCase().equals("a")) ? "cls" : flag; 
        
        boolean goodFlagLength = (hasFlag && flag.length() <= 4);
        goodFlagSyntax = goodFlagSyntax && 
            flag.matches("^(?:([CLScls])(?!\\1)){1,3}$");
        
        boolean goodFlag = ((goodFlagLength && goodFlagSyntax) || !hasFlag);

        if (goodLength && goodProtocol && goodPort && goodFlag) {
            try {
                InetAddress host = InetAddress.getByName(args[1]);
                AbstractMagicClient socket;
                if (protocol.equals("tcp")) {
                    socket = new MagicTcpClient(host, port, flag);
                } else {
                    socket = new MagicUdpClient(host, port, flag);
                }
                socket.printToStream(System.out);
            } catch (UnknownHostException e) {
                System.out.println("Host could not be found");
            } catch (IOException e) {
                System.out.println("An error has occured during communication");
            }
        }else{
            System.out.println(usage);
        }
    }
}
