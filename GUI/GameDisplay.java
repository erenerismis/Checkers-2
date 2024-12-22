package GUI;

import Game.Board;
import Game.Piece;
import Game.Player;

import javax.swing.*;
import java.awt.*;



public class GameDisplay extends JFrame {
    private Board board;
    private Player player1;
    private final int TILE_SIZE = 80;
    private Player player2;
    private JPanel boardPanel;
    private PlayerInfoWindow playerInfoWindow;
    private String player1Name;
    private String player2Name;
    private PlayerTimer player1Timer;
    private PlayerTimer player2Timer;
    private int timeLimit;


    public GameDisplay(Board board, Player player1, Player player2, int timeLimit) {
        this.board = board;


        setTitle("Checkers Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        boardPanel = createBoardPanel();
        add(boardPanel, BorderLayout.CENTER);


        playerInfoWindow = new PlayerInfoWindow(player1, player2);

        JButton showPlayerInfoButton = new JButton("Show Player Info");
        showPlayerInfoButton.addActionListener(e -> playerInfoWindow.setVisible(true));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(showPlayerInfoButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void render() {
        JPanel boardPanel = new JPanel(new GridLayout(board.getSize(), board.getSize()));
        boardPanel.setPreferredSize(new Dimension(board.getSize() * TILE_SIZE, board.getSize() * TILE_SIZE));
        JPanel bottomPanel = new JPanel();
        JButton showPlayerInfoButton = new JButton("Show Player Info");
        showPlayerInfoButton.addActionListener(e -> playerInfoWindow.setVisible(true));
        bottomPanel.add(showPlayerInfoButton);

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
        add(bottomPanel, BorderLayout.SOUTH);
        boardPanel.revalidate();
        boardPanel.repaint();
    }


    public void updateTitle(boolean isWhiteTurn) {
        String currentPlayer = isWhiteTurn ? player1Name : player2Name;
        setTitle("Checkers - Turn: " + currentPlayer + " - Time: " + timeLimit + " min");
    }


    private JPanel createBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(board.getSize(), board.getSize()));

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                JPanel tile = new JPanel();
                tile.setPreferredSize(new Dimension(80, 80));

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

                panel.add(tile);
            }
        }

        return panel;
    }


}