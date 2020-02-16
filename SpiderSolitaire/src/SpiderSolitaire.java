import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
public class SpiderSolitaire
{
    /** Number of stacks on the board **/
    public final int NUM_STACKS = 7;

    /** Number of complete decks used in the game.  
     *  The number of cards in a deck depends on the
     *  type of Card used.  For example, a 1-suit deck
     *  of standard playing cards consists of only 13 cards
     *  whereas a 4-suit deck consists of 52 cards.
     */
    public final int NUM_DECKS = 4;

    /** A Board contains stacks and a draw pile **/
    private Board board;

    /** Used for keyboard input **/
    private Scanner input;

    public SpiderSolitaire()
    {
        // Start a new game with NUM_STACKS stacks and NUM_DECKS of cards
        board = new Board(NUM_STACKS, NUM_DECKS);
        input = new Scanner(System.in);
    }

    /** Main game loop that plays games until user wins or quits **/
    public void play() {

        board.printBoard();
        boolean gameOver = false;

        while(!gameOver) {
            System.out.println("\nCommands:");
            System.out.println("   move [card] [source_stack] [destination_stack]");
            System.out.println("   draw");
            System.out.println("   clear [source_stack]");
            System.out.println("   restart");
            System.out.println("   save");
            System.out.println("   load");
            System.out.println("   quit");
            System.out.print(">");
            String command = input.next();

            if (command.equals("move")) {
                /* *** TO BE MODIFIED IN ACTIVITY 5 *** */
                String symbol = input.next();
                try{
                    int sourceStack = input.nextInt();
                    int destinationStack = input.nextInt();
                    board.makeMove(symbol, sourceStack, destinationStack);
                } catch(InputMismatchException e){
                    System.out.println(e.getMessage());
                    System.out.println("Invalid command.");
                } catch(NoSuchElementException e){
                    System.out.println(e.getMessage());
                    System.out.println("Invalid command.");
                }
            }
            else if (command.equals("draw")) {
                board.drawCards();
            }
            else if (command.equals("clear")) {
                /* *** TO BE MODIFIED IN ACTIVITY 5 *** */
                try{
                    int sourceStack = input.nextInt();
                    board.clear(sourceStack);
                } catch(InputMismatchException e){
                    System.out.println(e.getMessage());
                } catch(NoSuchElementException e){
                    System.out.println(e.getMessage());
                }
            }
            else if (command.equals("restart")) {
                board = new Board(NUM_STACKS, NUM_DECKS);
            }
            else if(command.equals("save")){
                board.save();
            }
            else if(command.equals("save")){
                board.restore();
            }
            else if (command.equals("quit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            else {
                System.out.println("Invalid command.");
            }

            board.printBoard();
            input.nextLine();

            // If all stacks and the draw pile are clear, you win!
            if (board.isEmpty()) {
                gameOver = true;
            }
        }
        System.out.println("Congratulations!  You win!");
    }
}
