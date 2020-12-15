package server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import common.*;

/**
 * Class that defines the type of cards that can be returned for a deck in 
 * Magic the Gathering.
 */
public class CardSource {

    private Card[] deck;
    private CardType typeFilter = null;
    
    /**
     * Create a new CardSource object to store and choose cards to send back to
     * the client.
     * @throws FileNotFoundException If the input file cannot be found
     */
    public CardSource() throws java.io.FileNotFoundException{
        this.deck = readCardCsv();
    }

    /**
     * Reads cards.csv for card data.
     * @return An array of Card objects
     * @throws FileNotFoundException If the file does not exist
     */
    private Card[] readCardCsv(){
        ArrayList<Card> cards = new ArrayList<>();
        
        try{

            Scanner sc = new Scanner(new File("server/cards.csv"));
            String cardString, cardName, cardType, cardMana;
            String[] cardData;
            Card newCard;
            short cardId;
            while(sc.hasNextLine()){
                cardString = sc.nextLine();
                cardData = cardString.split(",");
                cardId = Short.parseShort(cardData[0]);
                cardName = cardData[1];
                cardType = cardData[2];
                cardMana = cardData[3];
                newCard = new Card(cardId, cardName, cardType, cardMana);
                
                if(newCard.getType() != null){
                    cards.add(newCard);
                }
            }

            sc.close();
        }
        catch(IOException e){
            System.out.println("An error occured while reading cards.csv");
        }
        Card[] ar = new Card[cards.size()];
        return (Card[]) cards.toArray(ar);
    }

    /**
     * Displays the current deck to the screen.
     */
    public void displayDeck(){
        for(Card c : this.deck){
            System.out.println(c);
        }
    }

    /**
     * Gets a randomly chosen card to return to the client.
     * @return A randomly chosen card
     */
    public Card next(){
        Card card = null;
        Random rand = new Random();
        boolean cardFound = false;
        if(typeFilter != null){
            while(!cardFound){
                card = deck[rand.nextInt(deck.length)];
                cardFound = card.getType().getClassification()
                    .equals(this.typeFilter.getClassification());
            }
        }else{
            card = deck[rand.nextInt(deck.length)];
        }
        
        return card;
    }

    /**
     * Change the type of card allowed to be sent back to the client. Allowed
     * types are specified by @see CardType.
     * @param type Type of card allowed to be sent via the network
     */
    protected void setCardType(CardType type){
        this.typeFilter = type;
    }

    /**
     * Used for testing data input
     * @param args Not used
     */
    public static void main(String[] args){}
}
