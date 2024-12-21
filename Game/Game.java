package Game;

import GUI.GameDisplay;
import GUI.InputHandling;
import Logic.GameRules;
import javax.swing.*;
import java.awt.*;


public class Game {
    private Board board;
    private GameDisplay display;
    private GameRules rules;
    private boolean isWhiteTurn;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    private int boardSize = 8;
    private int timeLimit = 5;

    public void startLobby() {
        JFrame lobbyFrame = new JFrame("Checkers2-Lobby");
        lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lobbyFrame.setSize(400, 300);
        lobbyFrame.setLayout(new GridLayout(5, 2));


        JTextField player1Field = new JTextField(player1Name);
        JTextField player2Field = new JTextField(player2Name);
        lobbyFrame.add(new JLabel("Player 1 Name:"));
        lobbyFrame.add(player1Field);
        lobbyFrame.add(new JLabel("Player 2 Name:"));
        lobbyFrame.add(player2Field);


        JComboBox<String> boardSizeBox = new JComboBox<>(new String[]{"7x7", "8x8", "9x9", "10x10"});
        boardSizeBox.setSelectedIndex(1); //8x8
        lobbyFrame.add(new JLabel("Board Size:"));
        lobbyFrame.add(boardSizeBox);


        JComboBox<String> timeLimitBox = new JComboBox<>(new String[]{"1 Min", "5 Min", "10 Min"});
        timeLimitBox.setSelectedIndex(1); // (5 dk)
        lobbyFrame.add(new JLabel("Time Limit:"));
        lobbyFrame.add(timeLimitBox);


        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            player1Name = player1Field.getText();
            player2Name = player2Field.getText();
            boardSize = 7 + boardSizeBox.getSelectedIndex();
            timeLimit = switch (timeLimitBox.getSelectedIndex()) {
                case 0 -> 1;
                case 1 -> 5;
                case 2 -> 10;
                default -> 5;
            };

            lobbyFrame.dispose();
            start();
        });
        lobbyFrame.add(new JLabel());
        lobbyFrame.add(startButton);

        lobbyFrame.setVisible(true);
    }

    public void start() {
        board = new Board(boardSize);
        rules = new GameRules();
        display = new GameDisplay(board, player1Name, player2Name, timeLimit);
        isWhiteTurn = true;

        SwingUtilities.invokeLater(() -> {
            renderBoard();
            display.setVisible(true);
        });
    }

    public boolean makeMove(int startX, int startY, int endX, int endY) {
        System.out.println("DEBUG: makeMove called with start=(" + startX + ", " + startY + ") and end=(" + endX + ", " + endY + ")");
        Piece piece = board.getPieceAt(startX, startY);


        if (piece == null || !piece.getColor().equals(isWhiteTurn ? "White" : "Black")) {
            System.out.println("DEBUG: Wrong turn! Current turn: " + (isWhiteTurn ? "White" : "Black"));
            return false;
        }


        if (rules.hasMandatoryCapture(board, isWhiteTurn ? "White" : "Black")) {
            if (!isCaptureMove(startX, startY, endX, endY)) {
                System.out.println("You have a mandatory capture! Make that move.");
                return false;
            }
        }


        if (rules.isValidMove(board, startX, startY, endX, endY)) {
            board.movePiece(startX, startY, endX, endY);


            if (Math.abs(endX - startX) == 2 && Math.abs(endY - startY) == 2) {
                int midX = (startX + endX) / 2;
                int midY = (startY + endY) / 2;
                board.removePiece(midX, midY);
                System.out.println("DEBUG: Piece captured at (" + midX + ", " + midY + ")");


                if (rules.canCaptureAgain(board, endX, endY)) {
                    System.out.println("DEBUG: Must continue capturing!");
                    renderBoard();
                    return true;
                }
            }


            if (rules.shouldPromote(piece, endX, board.getSize())) {
                piece.promoteToKing();
                System.out.println("DEBUG: Piece promoted to King!");
            }



            isWhiteTurn = !isWhiteTurn;
            System.out.println("DEBUG: Turn updated. Next turn: " + (isWhiteTurn ? "White" : "Black"));

            renderBoard();
            if (isGameOver()) {
                System.out.println("The game has ended.");
                return false;
            }
            return true;

        }



        System.out.println("DEBUG: Invalid move from (" + startX + ", " + startY + ") to (" + endX + ", " + endY + ")");
        return false;
    }




    private boolean isCaptureMove(int startX, int startY, int endX, int endY) {
        int midX = (startX + endX) / 2;
        int midY = (startY + endY) / 2;

        Piece piece = board.getPieceAt(startX, startY);
        Piece midPiece = board.getPieceAt(midX, midY);

        return Math.abs(endX - startX) == 2 && Math.abs(endY - startY) == 2 &&
                midPiece != null &&
                !midPiece.getColor().equals(piece.getColor()) &&
                board.getPieceAt(endX, endY) == null;
    }



    private boolean isGameOver() {
        boolean whiteHasPieces = false;
        boolean blackHasPieces = false;
        boolean whiteHasMoves = false;
        boolean blackHasMoves = false;

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Piece piece = board.getPieceAt(i, j);

                if (piece != null) {
                    if (piece.getColor().equals("White")) {
                        whiteHasPieces = true;
                        if (rules.canCapture(board, i, j) || hasValidMove(piece, i, j)) {
                            whiteHasMoves = true;
                        }
                    } else if (piece.getColor().equals("Black")) {
                        blackHasPieces = true;
                        if (rules.canCapture(board, i, j) || hasValidMove(piece, i, j)) {
                            blackHasMoves = true;
                        }
                    }
                }
            }
        }

        if (!whiteHasPieces) {
            System.out.println("Game Over! Black wins!");
            return true;
        }
        if (!blackHasPieces) {
            System.out.println("Game Over! White wins!");
            return true;
        }

        if (!whiteHasMoves) {
            System.out.println("Game Over! Black wins! White has no valid moves.");
            return true;
        }
        if (!blackHasMoves) {
            System.out.println("Game Over! White wins! Black has no valid moves.");
            return true;
        }

        return false;
    }

    private boolean hasValidMove(Piece piece, int startX, int startY) {
        int[][] directions = piece.isKing() ? new int[][] {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}
                : piece.getColor().equals("White") ? new int[][] {{-1, -1}, {-1, 1}}
                : new int[][] {{1, -1}, {1, 1}};

        for (int[] dir : directions) {
            int endX = startX + dir[0];
            int endY = startY + dir[1];
            if (endX >= 0 && endX < board.getSize() && endY >= 0 && endY < board.getSize() &&
                    board.getPieceAt(endX, endY) == null) {
                return true;
            }
        }

        return false;
    }




    private void renderBoard() {
        display.render();
        display.updateTitle(isWhiteTurn);
        InputHandling inputHandling = new InputHandling(board, rules, this::renderBoard, this, isWhiteTurn);
        inputHandling.attachListeners((JPanel) display.getContentPane().getComponent(0));
    }


}

