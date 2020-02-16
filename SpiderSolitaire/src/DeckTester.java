import java.util.ArrayList;
public class DeckTester
{
    public static void main(String[] args) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 3 *** */
        Deck deck = new Deck();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card("A", 1));
        cards.add(new Card("K", 1));
        cards.add(new Card("Q", 12));
        cards.add(new Card("A", 1));
        cards.add(new Card("4", 4));
        cards.add(new Card("9", 9));
        for(int i = 0; i < cards.size(); i++) {
        	Card card = cards.get(i);
        	card.setFaceUp(true);
        }
        
        deck.addCardsToDeck(cards);
        System.out.println("Starting deck: " + deck);
        deck.shuffle();
        deck.sort();
        System.out.println("Ending deck: " + deck);
        /*
        Card topCard = deck.drawTopCard();
        System.out.print("Card drawn. Top card: value " + topCard.getValue());
        System.out.println(", symbol " + topCard.getSymbol());
        deck.shuffle();
        System.out.println("Shuffled.\n" + deck.toString() + "\n" + deck.getNumCards());
        Card topCard2 = deck.drawTopCard();
        System.out.print("Card drawn. Top card: value " + topCard2.getValue());
        System.out.println(", symbol " + topCard2.getSymbol());
        deck.shuffle();
        System.out.println("Shuffled.\n" + deck.toString() + "\n" + deck.getNumCards());
        */
    }
}
