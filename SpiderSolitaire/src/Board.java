/* Kristine McLaughlin Period 6 12/19/18 (final resubmission)
 * I spent around 8 hours altogether working on the game. The whole game
 * works, except for the save and load features. There's a compiler error I
 * don't know how to fix in the Board class in the save() method, with the
 * code from the instruction document. Before I fixed my program, I got a few
 * run time errors, specifically Index out of bounds errors. Another issue
 * with my original submission was that when I tried to remove a run from
 * a deck during a move, sometimes the whole run wouldn't be removed from
 * the source deck. That issue was because I was using the deck's number of
 * cards as a loop boundary, so it was changing and stopping short when I
 * didn't want it to.
 *  
 */
import java.util.ArrayList;
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import java.lang.reflect.InvocationTargetException;
import java.io.*;
public class Board{   
    // Attributes
    private ArrayList<Deck> allDecks = new ArrayList<>();
    private Deck drawPile;
    private ArrayList<Deck> completedDecks = new ArrayList<>();
    private ArrayList<Deck> playingDecks = new ArrayList<>();
    /**
     *  Sets up the Board and fills the stacks and draw pile from a Deck
     *  consisting of numDecks Decks.  The number of Cards in a Deck
     *  depends on the number of suits. Here are examples:
     *  
     *  # suits     # numDecks      #cards in overall Deck
     *      1            1          13 (all same suit)
     *      1            2          26 (all same suit)
     *      2            1          26 (one of each suit)
     *      2            2          52 (two of each suit)
     *      4            2          104 (two of each suit)
     *      
     *  Once the overall Deck is built, it is shuffled and half the cards
     *  are placed as evenly as possible into the stacks.  The other half
     *  of the cards remain in the draw pile.  If you'd like to specify
     *  more than one suit, feel free to add to the parameter list.
     */    
    public Board(int numStacks, int numDecks) {
        // make an arrayList of cards to go in a deck
        int totalCards = numDecks * 13;
        ArrayList<Card> allCards = new ArrayList<>();
        for(int i = 0; i < numDecks; i++){ // one suit
            allCards.add(new Card("A", 1));
            allCards.add(new Card("2", 2));
            allCards.add(new Card("3", 3));
            allCards.add(new Card("4", 4));
            allCards.add(new Card("5", 5));
            allCards.add(new Card("6", 6));
            allCards.add(new Card("7", 7));
            allCards.add(new Card("8", 8));
            allCards.add(new Card("9", 9));
            allCards.add(new Card("10", 10));
            allCards.add(new Card("J", 11));
            allCards.add(new Card("Q", 12));
            allCards.add(new Card("K", 13));
        }
        // put all cards in a deck
        Deck allCardsDeck = new Deck();
        allCardsDeck.addCardsToDeck(allCards);
        allCardsDeck.shuffle();
        // put half of the cards in the draw pile
        int cardsInDrawPile = allCardsDeck.getNumCards()/2;
        
        drawPile = allCardsDeck.drawTopStack(cardsInDrawPile);
        // distribute the rest of the cards into playing piles
        for(int i = 0; i < numStacks; i++){
            playingDecks.add(new Deck());
        }
        
        int currentStackNum = 1;
        for(int i = 0; i < totalCards/2; i++){
            if(currentStackNum > numStacks) currentStackNum = 1;
            Deck currentStack = playingDecks.get(currentStackNum-1);
            currentStack.addCard(allCardsDeck.getCardAt(i));
            currentStackNum++;
        }
        // flip the last card of each stack over
        for(int i = 0; i < numStacks; i++){
            Deck currentStack = playingDecks.get(i);
            Card lastCard = currentStack.getCardAt(currentStack.getNumCards()-1);
            lastCard.setFaceUp(true);
        }
    }

    /**
     *  Moves a run of cards from src to dest (if possible) and flips the
     *  next card if one is available.  Change the parameter list to match
     *  your implementation of Card if you need to.
     */
    public void makeMove(String symbol, int src, int dest) {
        Deck sourceDeck = playingDecks.get(src-1);
        Deck destinationDeck = playingDecks.get(dest-1);
        if(sourceDeck.getNumCards() > 0 && destinationDeck.getNumCards() > 0){
            // make a separate deck for the target card and the end of the stack
            int symbolIndex = -1;
            ArrayList<Card> symbolToEnd = new ArrayList<>();
            int lastCard = sourceDeck.getNumCards()-1;
            for(int i = lastCard; i >= 0; i--){
                Card currentCard = sourceDeck.getCardAt(i);
                symbolToEnd.add(0, currentCard);
                if(currentCard.getSymbol().equals(symbol) && currentCard.isFaceUp()){
                    symbolIndex = i;
                    break;
                }
            }
            if(symbolIndex <= -1){
                System.out.println("Illegal move.");
            } else if(validMove(symbolIndex, src, dest)){
                // remove the symbolToEnd stack from the source deck
                int numCards = sourceDeck.getNumCards();
                for(int i = symbolIndex; i < numCards; i++){
                    sourceDeck.removeCard(symbolIndex);
                }
                // add the symbolToEnd stack to the destination stack
                destinationDeck.addCardsToDeck(symbolToEnd);
                // flip card before the target card in sourceDeck if not flipped
                if(symbolIndex-1 >= 0 && symbolIndex-1 < sourceDeck.getNumCards()){
                    Card cardBefore = sourceDeck.getCardAt(symbolIndex-1);
                    if(!cardBefore.isFaceUp()) cardBefore.setFaceUp(true);
                }
            } else System.out.println("Illegal move.");
        } else System.out.println("Illegal move.");
    }
    // helper method for validMove
    private boolean validMove(int symbolIndex, int src, int dest){
        Deck sourceDeck = playingDecks.get(src-1);
        Deck destinationDeck = playingDecks.get(dest-1);
        Card symbolCard = sourceDeck.getCardAt(symbolIndex);
        int nextValue = symbolCard.getValue();
        
        // condition 3: the last card of the destination stack must create
        // a longer, valid run
        Card destinationCard = destinationDeck.getCardAt(destinationDeck.getNumCards()-1);
        int destinationVal = destinationCard.getValue();
        if(destinationVal != nextValue + 1) return false;
        
        // condition 1 and 2: must be a run from symbolIndex to the end of 
        // source stack, and all cards in the run must be face up
        for(int i = symbolIndex; i < sourceDeck.getNumCards(); i++){
            Card currentCard = sourceDeck.getCardAt(i);
            /*
            if(i == sourceDeck.getNumCards()-1 && currentCard.isFaceUp() &&
            currentCard.getValue() == nextValue){
                return true;
            }
            */
            if(nextValue != currentCard.getValue() || !currentCard.isFaceUp()) 
                return false;
            nextValue--;
        }
        
        if(destinationDeck.getNumCards() == 0) return true;
        return true;
    }

    /** 
     *  Moves one card onto each stack, or as many as are available
     */
    public void drawCards() {
        if(drawPile.getNumCards() == 0) System.out.println("No cards left in the draw pile.");
        boolean canDraw = true;
        for(int i = 0; i < playingDecks.size(); i++){
            if(playingDecks.get(i).getNumCards() == 0) canDraw = false;
        }
        if(canDraw){
            for(int i = 0; i < playingDecks.size(); i++){
                if(drawPile.getNumCards() > 0){
                    Card nextCard = drawPile.getCardAt(drawPile.getNumCards()-1);
                    nextCard.setFaceUp(true);
                    Deck currentStack = playingDecks.get(i);
                    currentStack.addCard(nextCard);
                    drawPile.removeCard(drawPile.getNumCards()-1);
                }
            }
        } else{
            System.out.println("You cannot draw cards with an empty stack present.");
        }
    }

    /**
     *  Returns true if all stacks and the draw pile are all empty
     */ 
    public boolean isEmpty() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 5 *** */
        for(int i = 0; i < playingDecks.size(); i++){
            Deck currentDeck = playingDecks.get(i);
            if(currentDeck.getNumCards() != 0) return false;
        }
        if(drawPile.getNumCards() != 0) return false;
        return true;
    }

    /**
     *  If there is a run of A through K starting at the end of sourceStack
     *  then the run is removed from the game or placed into a completed
     *  stacks area.
     *  
     *  If there is not a run of A through K starting at the end of sourceStack
     *  then an invalid move message is displayed and the Board is not changed.
     */
    public void clear(int sourceStack) {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 5 *** */
        if(sourceStack < 1 || sourceStack > playingDecks.size()) 
            System.out.println("Invalid move, please enter a stack number shown below.");
        else if(hasRun(sourceStack)){
            Deck stack = playingDecks.get(sourceStack-1);
            int kingPosition = stack.getNumCards()-13;
            int acePosition = stack.getNumCards()-1;
            Deck runDeck = new Deck();
            for(int i = acePosition; i >= kingPosition; i--){
                Card currentCard = stack.getCardAt(i);
                runDeck.addCard(currentCard);
                stack.removeCard(i);
            }
            completedDecks.add(runDeck);
        } else{
            System.out.println("Invalid move, run of A to K not present.");
        }
    }
    private boolean hasRun(int sourceStack){ // helper method for clear()
        Deck stack = playingDecks.get(sourceStack-1);
        int cardValue = 1;
        for(int i = stack.getNumCards()-1; i >= 0; i--){
            Card currentCard = stack.getCardAt(i);
            if(currentCard.isFaceUp() && currentCard.getValue() == 13 &&
            cardValue == 13){
                return true;
            }
            if(currentCard.isFaceUp() && cardValue != currentCard.getValue()) 
                return false;
            cardValue++;
        }
        return false;
    }

    /**
     * Prints the board to the terminal window by displaying the stacks, draw
     * pile, and done stacks (if you chose to have them)
     */
    public void printBoard() {
        /* *** TO BE IMPLEMENTED IN ACTIVITY 4 *** */
        System.out.println("Stacks: ");
        for(int i = 0; i < playingDecks.size(); i++){
            System.out.print((i + 1) + ": ");
            Deck currentStack = playingDecks.get(i);
            System.out.println(currentStack);
        }
        
        System.out.println("\nDraw Pile: ");
        System.out.println(drawPile);
        
        if(completedDecks.size() == 0) System.out.println("\nCompleted Decks: None");
        else {
            System.out.println("Completed Decks: ");
            for(int i = 0; i < completedDecks.size(); i++){
                System.out.print((i + 1) + ": ");
                Deck currentStack = completedDecks.get(i);
                System.out.println(currentStack);
            }
        }
    }
    
    // This method has the compiler error, so I commented it out to be able
    // to run the rest of the program.
    public void save(){
       
        /*
        try {
            EventQueue.invokeAndWait(new Runnable()); 
            @Override
            public void run(){
                // Create a JFileChooser that points to the current directory
                JFileChooser chooser = new JFileChooser(".");
                
                // Tell the JFileChooser to show a Save dialog window
                chooser.showSaveDialog(null);
                
                // Ask the JFileChooser for the File the user typed in or selected
                File f = chooser.getSelectedFile();
                
                // Create a FileWriter that can write to the selected File
                FileWriter writer = new FileWriter(f);
            }
            
        } catch (InterruptedException e) {
		System.out.println("Error: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println("Error: " + e.getMessage());
        }
        */
    }
    public void restore(){
        JFileChooser chooser = new JFileChooser(".");
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        try{
            FileWriter writer = new FileWriter(f);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}