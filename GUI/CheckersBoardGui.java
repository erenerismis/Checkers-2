package GUI;

import Piece.Piece;  // Ensure you have a Piece class for checkers pieces
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;

public class CheckersBoardGui extends JPanel {

    private int tileSize;
    private ArrayList<Piece> pieceList;  // List of pieces on the board
    private Piece selectedPiece;

    public CheckersBoardGui() {
        pieceList = new ArrayList<>();
        // Initialize other components like tile size, piece list, etc.
        // Setup board layout, tile size, etc.
    }

    // Override the paintComponent to display the board and pieces
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the checkers board grid
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                g.setColor((row + col) % 2 == 0 ? java.awt.Color.WHITE : java.awt.Color.BLACK);
                g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }

        // Draw the checkers pieces
        for (Piece piece : pieceList) {
            piece.paint(g);  // Assuming you have a method `paint(Graphics g)` in your Piece class
        }
    }

    // Method to add a piece to the board
    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }

    public int getTileSize() {
        return tileSize;
    }
}
