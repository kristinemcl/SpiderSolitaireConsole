public class CardTester
{
    public static void main(String[] args) {
        // testing Card class
        Card greaterCard = new Card("Club", 9);
        greaterCard.setFaceUp(false);
        Card lesserCard = new Card("Spades", 10);
        lesserCard.setFaceUp(true);
        System.out.println("Card 1:\n" + greaterCard.getSymbol() + " " + greaterCard.getValue());
        System.out.println(greaterCard.toString() + " " + greaterCard.isFaceUp());
        System.out.println(greaterCard.compareTo(lesserCard));
        
        System.out.println("\nCard 2:\n" + lesserCard.getSymbol() + " " + lesserCard.getValue());
        System.out.println(lesserCard.toString() + " " + lesserCard.isFaceUp());
        System.out.println(lesserCard.compareTo(greaterCard));
        
        /* *** TO BE IMPLEMENTED IN ACTIVITY 2 *** */
    }
}
