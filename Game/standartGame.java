package Game;

import GUI.GUI;
import Piece.Piece;
import java.util.ArrayList;

public class StandardGame {

    // Declare static players for Player 1 (White) and Player 2 (Black)
    static public Player player1;
    static public Player player2;

    // Game state to track turns and moves
    static public boolean isWhiteTurn = true;  // White player starts first

    public void startGame() {
        // Initialize the two players
        player1 = new Player(Color.WHITE, true); // White player starts
        player2 = new Player(Color.BLACK, false); // Black player goes second

        // Initialize the game board
        Board board = new Board();
        board.setupInitialBoard(); // Set up pieces on the board
    }

    /**
     * Returns the opponent based on the current player's color
     */
    public static Player getOpponent(Color color) {
        if (color == Color.BLACK) {
            return player1; // If black, opponent is white
        } else {
            return player2; // If white, opponent is black
        }
    }

    /**
     * Method to switch turns between players after a move
     */
    public static void switchTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    public static void main(String[] args) {
        // Start the game
        StandardGame game = new StandardGame();
        game.startGame();

        // Start the GUI for user interaction (if implemented in the GUI class)
        GUI.startGame();
    }
}
