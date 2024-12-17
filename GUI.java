import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
    private static final int BOARD_SIZE = 8;
    private JPanel[][] squares = new JPanel[BOARD_SIZE][BOARD_SIZE];
    private JLabel selectedPiece = null;
    private int selectedRow = -1, selectedCol = -1;

    public GUI() {
        JFrame frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JPanel square = new JPanel();
                square.setLayout(new BorderLayout());
                square.setName(row + "," + col);

                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.BLACK);

                    if (row < 3) {
                        square.add(createPiece(Color.DARK_GRAY));
                    } else if (row > 4) {
                        square.add(createPiece(Color.LIGHT_GRAY));
                    }
                }

                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(square, row, col);
                    }
                });

                squares[row][col] = square;
                boardPanel.add(square);
            }
        }

        frame.add(boardPanel);
        frame.setVisible(true);
    }

    private JLabel createPiece(Color color) {
        JLabel piece = new JLabel();
        piece.setOpaque(true);
        piece.setBackground(color);
        piece.setPreferredSize(new Dimension(50, 50));
        piece.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        piece.setHorizontalAlignment(SwingConstants.CENTER);
        piece.setVerticalAlignment(SwingConstants.CENTER);
        return piece;
    }

    private void handleSquareClick(JPanel square, int row, int col) {
        if (selectedPiece == null) {
            if (square.getComponentCount() > 0) {
                selectedPiece = (JLabel) square.getComponent(0);
                selectedRow = row;
                selectedCol = col;
                square.remove(selectedPiece);
                square.revalidate();
                square.repaint();
            }
        } else {
            if (isValidMove(row, col)) {
                square.add(selectedPiece);
                square.revalidate();
                square.repaint();
                selectedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            } else {
                squares[selectedRow][selectedCol].add(selectedPiece);
                squares[selectedRow][selectedCol].revalidate();
                squares[selectedRow][selectedCol].repaint();
                selectedPiece = null;
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }

    private boolean isValidMove(int targetRow, int targetCol) {
        if ((targetRow + targetCol) % 2 == 0) {
            return false;
        }

        int rowDiff = Math.abs(targetRow - selectedRow);
        int colDiff = Math.abs(targetCol - selectedCol);
        return rowDiff == 1 && colDiff == 1;
    }

    public static void main(String[] args) {
        new GUI();
    }
}
