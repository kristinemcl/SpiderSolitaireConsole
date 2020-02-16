import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class Deck
{
    private ArrayList<Card> cardsInDeck;
    private int numCards;
    public Deck(){
        cardsInDeck = new ArrayList<Card>();
        numCards = 0;
    }
    public Deck(String str){
        cardsInDeck = new ArrayList<>();
        int count = 0; // 1 = symbol, 2 = value, 3 = faceUp
        String symbol = "";
        String value = "";
        String faceUp = "";
        for(int i = 0; i < str.length(); i++){
            String word = nextWord(str, i);
            if(!word.equals("") && !word.equals(".")) count++;
            if(count == 1){
                symbol = word;
                i += word.length()-1;
            } else if(count == 2){
                value = word;
                i += word.length()-1;
            }else if(count == 3){
                faceUp = word;
                int val = Integer.parseInt(value);
                Card card = new Card(symbol, val);
                if(faceUp.equals("true")) card.setFaceUp(true);
                else card.setFaceUp(false);
                cardsInDeck.add(card);
                count = 0;
                i += word.length()-1;
            }
        }
    }
    private String nextWord(String str, int startIndex){
        String word = "";
        for(int i = startIndex; i < str.length(); i++){
            if(Character.isLetter(str.charAt(i)) || Character.isDigit(str.charAt(i))) 
                word += str.charAt(i);
            else break;
        }
        return word;
    }
    
    // ADDED METHOD FOR SELECTIONSORT LAB
    // ascending order, swap largest Cards to the right
    public void sort() {
    	for(int numPass = 0; numPass < numCards-1; numPass++) {
			int highest = 0;
			for(int index = 0; index < numCards-numPass; index++) {
				if(cardsInDeck.get(index).compareTo(cardsInDeck.get(highest)) > 0) highest = index;
			}
			// switch the highest element and the right-most element of the pass
			Card last = cardsInDeck.get(numCards-1-numPass);
			cardsInDeck.set(numCards-1-numPass, cardsInDeck.get(highest));
			cardsInDeck.set(highest, last);
			System.out.println(cardsInDeck);
		}
    }
    
    public void addCardsToDeck(ArrayList<String> symbols, ArrayList<Integer> values){
        for(int i = 0; i < symbols.size(); i++){
            cardsInDeck.add(new Card(symbols.get(i), values.get(i)));
        }
        numCards = cardsInDeck.size();
    }
    public void addCardsToDeck(ArrayList<Card> cards){
        for(int i = 0; i < cards.size(); i++){
            cardsInDeck.add(cards.get(i));;
        }
        numCards = cardsInDeck.size();
    }
    public void addCard(Card newCard){
        cardsInDeck.add(newCard);
        numCards = cardsInDeck.size();
    }
    public void addCardAt(Card newCard, int index){
        cardsInDeck.add(index, newCard);
        numCards = cardsInDeck.size();
    }
    
    public void removeCard(int index){
        cardsInDeck.remove(index);
        numCards = cardsInDeck.size();
    }
    
    public Card getCardAt(int index){
        numCards = cardsInDeck.size();
        if(index >= cardsInDeck.size()) throw new IndexOutOfBoundsException();
        else return cardsInDeck.get(index);
    }
    
    public void shuffle(){
        for(int i = 0; i < cardsInDeck.size(); i++){
            Random rand = new Random();
            int randomIndex = rand.nextInt(cardsInDeck.size());
            Card card1 = cardsInDeck.get(i);
            Card card2 = cardsInDeck.get(randomIndex);
            cardsInDeck.remove(i);
            cardsInDeck.add(i, card2);
            cardsInDeck.remove(randomIndex);
            cardsInDeck.add(randomIndex, card1);
        }
    }
    // pre condition: the deck has to have at least 1 card
    public Card drawTopCard(){
        if(cardsInDeck.size() >= 1){
            Card topCard = cardsInDeck.get(0);
            cardsInDeck.remove(0);
            numCards = cardsInDeck.size();
            return topCard;
        } else{
            System.out.println("There are no cards in the deck to draw from. At least one card must be present.");
            return new Card("No card", 0);
        }
    }
    public Deck drawTopStack(int numCards){
        Deck d = new Deck();
        for(int i = 0; i < numCards; i++){
            d.addCard(cardsInDeck.get(0));
            cardsInDeck.remove(0);
        }
        numCards = cardsInDeck.size();
        return d;
    }
    public int getNumCards(){
        numCards = cardsInDeck.size();
        return numCards;
    }
    @Override
    public String toString(){
        return cardsInDeck.toString();
    }
    // for save and load
    public String completeDeck(){
        String finalS = "";
        for(int i = 0; i < numCards; i++){
            Card current = cardsInDeck.get(i);
            finalS += current.getSymbol();
            finalS += current.getValue();
            finalS += current.isFaceUp() + ". ";
        }
        return finalS;
    }
}
