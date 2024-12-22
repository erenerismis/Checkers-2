package GUI;

import Game.Board;
import Game.Piece;

import javax.swing.*;
import java.awt.*;

public class GameDisplay extends JFrame {
    private Board board;
    private final int TILE_SIZE = 80;
    private String player1Name;
    private String player2Name;
    private int timeLimit;

    public GameDisplay(Board board, String player1Name, String player2Name, int timeLimit) {
        this.board = board;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.timeLimit = timeLimit;

        setTitle("Checkers 2");
        setSize(board.getSize() * TILE_SIZE, board.getSize() * TILE_SIZE + 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }


    public void render() {
        JPanel boardPanel = new JPanel(new GridLayout(board.getSize(), board.getSize()));
        boardPanel.setPreferredSize(new Dimension(board.getSize() * TILE_SIZE, board.getSize() * TILE_SIZE));

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                JPanel tile = new JPanel();
                tile.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));


                if ((i + j) % 2 == 0) {
                    tile.setBackground(Color.LIGHT_GRAY);
                } else {
                    tile.setBackground(Color.DARK_GRAY);


                    Piece piece = board.getPieceAt(i, j);
                    if (piece != null) {
                        JLabel pieceLabel = new JLabel(piece.isKing() ? "K" : "P");
                        pieceLabel.setForeground(piece.getColor().equals("White") ? Color.WHITE : Color.BLACK);
                        pieceLabel.setFont(new Font("Arial", Font.BOLD, 30));
                        pieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        tile.add(pieceLabel);
                    }
                }
                boardPanel.add(tile);
            }
        }


        getContentPane().removeAll();
        add(boardPanel);
        revalidate();
        repaint();
    }


    public void updateTitle(boolean isWhiteTurn) {
        String currentPlayer = isWhiteTurn ? player1Name : player2Name;
        setTitle("Checkers - Turn: " + currentPlayer + " - Time: " + timeLimit + " min");
    }

}
