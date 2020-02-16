/* Kristine McLaughlin, Period 6, 12/2/18
 * This part of the lab (activity 1-3) took me around 30 minutes. I found 
 * the tasks to be pretty easy, especially since they were very simple and 
 * just consisted of creating classes and methods, but also because the
 * instructions were pretty straightforward. The one challenge/thing that I
 * was confused about was the compareTo method in the Card class, because
 * in the API for this method, it says it throws two errors. I tried using
 * a try catch block but the method wanted me to return what I stated, so
 * instead I just used -2 as the output for when the card passed in is null 
 * (I'm not sure if that's right but I didn't know how else to handle this
 * issue).
 */
/**
 * Card.java
 *
 * <code>Card</code> represents a basic playing card.
 */
public class Card implements Comparable<Card>
{
    /** String value that holds the symbol of the card.
    Examples: "A", "Ace", "10", "Ten", "Wild", "Pikachu"
     */
    private String symbol;

    /** int value that holds the value this card is worth */
    private int value;

    /** boolean value that determines whether this card is face up or down */
    private boolean isFaceUp;

    /**
     * Creates a new <code>Card</code> instance.
     *
     * @param symbol  a <code>String</code> value representing the symbol of the card
     * @param value an <code>int</code> value containing the point value of the card
     */    
    public Card(String symbol, int value) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
        this.symbol = symbol;
        this.value = value;
        isFaceUp = false;
    }

    /**
     * Getter method to access this <code>Card</code>'s symbol.
     * 
     * @return this <code>Card</code>'s symbol.
     */
    public String getSymbol() {
        return symbol;
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
    }
    
    /**
     * Getter method to access this <code>Card</code>'s value.
     * @return this <code>Card</code>'s value.
     */
    public int getValue() {
        return value;
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
    }
    
    /**
     * Getter method to access whether this <code>Card</code> is face down or up.
     * @return true if this <code>Card</code> is face up, and false if this
     * <code>Card</code> is face down. 
     */
    public boolean isFaceUp() {
        return isFaceUp;
    }
    
    /**
     * Setter method to set the state of the <code>Card</code> to face down or up.
     * @param state the new state of the <code>Card</code>, true for face up 
     * and false for face down
     */
    public void setFaceUp(boolean state) {
        isFaceUp = state;
    }

    /**
     * Returns whether or not this <code>Card</code> is equal to another
     *  
     *  @return whether or not this Card is equal to other.
     */
    public boolean equals(Card other) {
        int otherValue = other.getValue();
        return value == otherValue;
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
    }
    
    /**
     * Compares this <code>Card</code>'s value to another <code>Card</code>'s value.
     * @param other the <code>Card</code> to compare this <code>Card</code> to
     * @returns -1, 0, or 1 if this <code>Card</code>'s value is less than the 
     * <code>Card</code> passed in to the parameter's value. Returns -2 if the 
     * card passed in is null.
     */
    @Override
    public int compareTo(Card other){
        if(other != null){
            int otherValue = other.getValue();
            if(value == otherValue) return 0;
            else if(value > otherValue) return 1;
            else return -1;
        } else return -2;
    }
    
    /**
     * Returns this card as a String.  If the card is face down, "X"
     * is returned.  Otherwise the symbol of the card is returned.
     *
     * @return a <code>String</code> containing the symbol and point
     * value of the card.
     */
    @Override
    public String toString() {
        if(!isFaceUp) return "X";
        else{
            return symbol;
        }
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
    }
}
