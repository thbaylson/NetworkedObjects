package common;

import java.io.Serializable;

/**
 * Class represents simple cards in the game of Magic the Gathering.
 */
public class Card implements Serializable{

    /**I honestly have no idea why I need this here*/
    private static final long serialVersionUID = 1L;

    /**Card location in the input file */
    private short id;

    /**Name of the card */
    private String cardName;

    /**Which type of card (spell, creature, land, etc) */
    private CardType type;

    /**Energy required to use this card */
    private String mana;

    /**
     * Create a single card for magic the gathering.
     * @param id Card location in the input file
     * @param name Name of the card
     * @param type Which type of card (spell, creature, land, etc)
     * @param mana Energy required to use this card
     */
    public Card(short id, String name, CardType type, String mana) {
        this.id = id;
        this.cardName = name;
        this.type = type;
        this.mana = mana;
    }

    /**
     * Create a single card for magic the gathering.
     * @param id Card location in the input file
     * @param name Name of the card
     * @param type Which type of card (spell, creature, land, etc)
     * @param mana Energy required to use this card
     */
    public Card(short id, String name, String type, String mana) {
        this.id = id;
        this.cardName = name;
        this.type = findType(type);
        this.mana = mana;
    }

    /**
     * Search through the card's types and find the appropriate CardType.
     * @return The appropriate CardType
     */
    private CardType findType(String type){
        //Grab the types from the CardType Enum
        CardType[] types = CardType.values();
        CardType actualType = null;
        String typeName;

        //Grab the names that we will find in the file and check them against
        //the 3 types we have flags for: creature, land, spell.
        for(int i = 0; i < types.length; i++){
            typeName = types[i].getName();
            if(type.contains(typeName)){
                actualType = types[i];
            }
        }

        return actualType;
    }

    /**
     * Get card's unique id number
     */
    public short getId(){
        return this.id;
    }

    /**
     * Get card's name
     */
    public String getName(){
        return this.cardName;
    }

    /**
     * Get card's type
     */
    public CardType getType(){
        return this.type;
    }

    /**
     * Change a card's type
     * @param type A card type to change to
     */
    public void setType(CardType type){
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format("%30s: %10s ( %4s)", 
            this.cardName, 
            this.type.getClassification(),
            this.mana);
    }
}
