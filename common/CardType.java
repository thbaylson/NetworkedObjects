package common;

/**
 * Creature, Land, Enchantment, Sorcery, Instant, etc
 */
public enum CardType {
    Creature("Creature", "Creature"),
    Land("Land", "Land"),
    Instant("Instant", "Spell"),
    Sorcery("Sorcery", "Spell"),
    Spell("Spell", "Spell"),
    Enchantment("Enchantment", "Spell");
    
    /**The official name of the card type */
    private final String name;
    
    /**The classification of the card type for purposes of this application */
    private final String classification;

    private CardType(String name, String classification){
        this.name = name;
        this.classification = classification;
    }

    /**
     * Returns the name of the card type
     * @return The name of the card type
     */
    public String getName(){
        return this.name;
    }

    /**
     * Return the classification of the card type
     * @return The classification of the card type
     */
    public String getClassification(){
        return this.classification;
    }
}
