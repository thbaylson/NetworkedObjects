package server;

import java.io.FileNotFoundException;

import common.CardType;

/**
 * An abstract class that contains fields and methods that may be common to
 * implementations of the 'chargen' server.
 */
public abstract class AbstractMagicServer implements MagicServer{

    /**The default port on which a magic server lives */
    protected static final int DEFAULT_PORT = 5500;
    
    /**The default number of items to send back */
    protected static final int NUM_ITEMS = 20;

    /**Example: <protocol> <address> -L */
    protected static int ONE_TYPE = 20;

    /**Example: <protocol> <address> -LC */
    protected static int TWO_TYPES = 40;
    
    /**Example: <protocol> <address> -A */
    protected static int THREE_TYPES = 60;

    /**The current card source to use */
    protected CardSource source;
    
    /**The current port to listen on */
    protected int port;

    /**The number of items to send to the server */
    protected int numItems;

    /**
     * Initializes a new AbstractMagicServer using the default port and the
     * default source.
     */
    public AbstractMagicServer() throws FileNotFoundException{
        this(DEFAULT_PORT, null);
    }

    /**
     * Initializes a new AbstractMagicServer using the specified port and the
     * default source.
     * @param port The port to which the server will bind to and listen on.
     */
    public AbstractMagicServer(int port) throws FileNotFoundException{
        this(port, null);
    }

    /**
     * Initializes a new AbstractMagicServer using the default port and the
     * specified source.
     * @param source The source to use to send cards to connecting clients.
     */
    public AbstractMagicServer(CardSource source) throws FileNotFoundException{
        this(DEFAULT_PORT, source);
    }

    /**
     * Initializes a new AbstractMagicServer using the specified port and the
     * specified source.
     * @param port The port to which the server will bind to and listen on.
     * @param source The source to use to send cards to connecting clients.
     */
    public AbstractMagicServer(int port, CardSource source) throws FileNotFoundException{
        this.port = port;
        if(this.source == null){
            this.source = new CardSource();
        }else{
            this.source = source;
        }
    }

    /**
     * Initializes a new AbstractMagicServer using the specified port, card
     * source, and number of items to send.
     * @param port The port to which the server will bind to and listen on.
     * @param source The source to use to send cards to connecting clients.
     * @param numItems The number of items to send over the network.
     */
    public AbstractMagicServer(int port, CardSource source, int numItems){
        this.port = port;
        this.source = source;
        this.numItems = numItems;
    }
    
    /**
     * Change how many items to send back to the client. This number cannot
     * fall below the default of 20 items.
     * @param numItems The number of items to send back to the client.
     */
    protected void changeItemsToSend(int numItems){
        this.numItems = numItems;
    }
    
    /**
     * Change which source is being used to generate characters for the server.
     * @param source A CardSource used to generate cards.
     */
    protected void changeSource(CardSource source){
        this.source = source;
    }
    
    /**
     * Get the port to which the server will bind and listen for incoming
     * connections.
     * @return The port to which the server will bind to and listen on.
     */
    protected int getPort(){
        return this.port;
    }

    /**
     * Determine the current number of items we should be sending back to the
     * client.
     * @return The number of items to send back to the client.
     */
    protected int getItemsToSend(){
        return this.numItems;
    }
    
    /**
     * Get the CardSource to use for generating the character stream to send to
     * clients.
     * @return The CardSource used to generate the cards being sent to clients.
     */
    protected CardSource getSource(){
        return this.source;
    }
    
    /**
     * Determine the type (Spell, Creature, land, or some combination thereof)
     * as well as the number returned to the client. Three types of cards
     * returns 60 cards, 2 types 40, 1 type 20.
     * @param command The flag data returned from the server.
     */
    protected void setCardsReturned(String command){
        if(command.length() == 1){
            switch(command.toLowerCase()){
                case "a":
                this.source.setCardType(null);
                break;
                case "c":
                this.source.setCardType(CardType.Creature);
                break;
                case "l":
                this.source.setCardType(CardType.Land);
                break;
                case "s":
                this.source.setCardType(CardType.Spell);
                break;
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void listen() throws MagicServerException;

}
