package GUI;
import Game.Game;

import Game.Board;
import Game.Piece;
import Logic.GameRules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class InputHandling {
    private int startX = -1, startY = -1;
    private Board board;
    private GameRules rules;
    private Runnable onMoveCompleted;
    private Game game;
    private boolean isWhiteTurn;
    private List<int[]> possibleMoves;
    private List<int[]> captureMoves;

    public InputHandling(Board board, GameRules rules, Runnable onMoveCompleted, Game game, boolean isWhiteTurn) {
        this.board = board;
        this.rules = rules;
        this.onMoveCompleted = onMoveCompleted;
        this.game = game;
        this.isWhiteTurn = isWhiteTurn;
        this.possibleMoves = new ArrayList<>();
        this.captureMoves = new ArrayList<>();
    }

    public void attachListeners(JPanel boardPanel) {
        Component[] tiles = boardPanel.getComponents();
        for (int i = 0; i < tiles.length; i++) {
            int row = i / board.getSize();
            int col = i % board.getSize();
            JPanel tile = (JPanel) tiles[i];

            tile.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    handleTileClick(row, col, boardPanel);
                }
            });
        }
    }

    private void handleTileClick(int row, int col, JPanel boardPanel) {
        if (startX != -1 && startY != -1) {

            resetTileHighlight(boardPanel);
        }

        if (startX == -1 && startY == -1) {

            Piece piece = board.getPieceAt(row, col);
            if (piece != null) {

                if ((isWhiteTurn && piece.getColor().equals("White")) ||
                        (!isWhiteTurn && piece.getColor().equals("Black"))) {
                    startX = row;
                    startY = col;
                    System.out.println("DEBUG: Selected piece at (" + startX + ", " + startY + ")");


                    possibleMoves = calculatePossibleMoves(piece, startX, startY);
                    captureMoves = calculateCaptureMoves(piece, startX, startY);


                    if (!captureMoves.isEmpty()) {
                        highlightCaptureMoves(boardPanel);
                    } else {
                        highlightPossibleMoves(boardPanel);
                    }
                } else {
                    System.out.println("DEBUG: Wrong turn! Current turn: " + (isWhiteTurn ? "White" : "Black"));
                }
            } else {
                System.out.println("DEBUG: No piece at position (" + row + ", " + col + ")");
            }
        } else {

            System.out.println("DEBUG: Attempting move from (" + startX + ", " + startY + ") to (" + row + ", " + col + ")");


            boolean moveSuccessful = game.makeMove(startX, startY, row, col);

            if (moveSuccessful) {
                startX = -1;
                startY = -1;
                possibleMoves.clear();
                captureMoves.clear();
                onMoveCompleted.run();
            } else {
                System.out.println("DEBUG: Move failed. Invalid move.");
                startX = -1;
                startY = -1;
                possibleMoves.clear();
                captureMoves.clear();
            }
        }
    }


    private void resetTileHighlight(JPanel boardPanel) {
        Component[] tiles = boardPanel.getComponents();
        for (Component tile : tiles) {
            JPanel panelTile = (JPanel) tile;


            if (panelTile.getBackground() == Color.LIGHT_GRAY) {
                panelTile.setBackground(Color.LIGHT_GRAY);
            } else {
                panelTile.setBackground(Color.DARK_GRAY);
            }
        }
    }



    private List<int[]> calculatePossibleMoves(Piece piece, int startX, int startY) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions;


        if (piece.isKing()) {
            directions = new int[][] {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        } else if (piece.getColor().equals("White")) {
            directions = new int[][] {{-1, -1}, {-1, 1}};
        } else {
            directions = new int[][] {{1, -1}, {1, 1}};
        }

        for (int[] dir : directions) {
            int currentX = startX + dir[0];
            int currentY = startY + dir[1];


            while (currentX >= 0 && currentX < board.getSize() && currentY >= 0 && currentY < board.getSize()) {
                Piece targetPiece = board.getPieceAt(currentX, currentY);
                if (targetPiece == null) {
                    moves.add(new int[]{currentX, currentY});
                } else {
                    break;
                }

                if (piece.isKing()) {
                    currentX += dir[0];
                    currentY += dir[1];
                } else {
                    break;
                }
            }
        }

        return moves;
    }


    private List<int[]> calculateCaptureMoves(Piece piece, int startX, int startY) {
        List<int[]> captures = new ArrayList<>();
        int[][] directions;

        if (piece.isKing()) {
            directions = new int[][] {{2, 2}, {2, -2}, {-2, 2}, {-2, -2}};
        } else if (piece.getColor().equals("White")) {
            directions = new int[][] {{-2, -2}, {-2, 2}};
        } else {
            directions = new int[][] {{2, -2}, {2, 2}};
        }

        for (int[] dir : directions) {
            int midX = startX + dir[0] / 2;
            int midY = startY + dir[1] / 2;
            int endX = startX + dir[0];
            int endY = startY + dir[1];


            if (endX >= 0 && endX < board.getSize() && endY >= 0 && endY < board.getSize()) {
                Piece midPiece = board.getPieceAt(midX, midY);
                if (midPiece != null && !midPiece.getColor().equals(piece.getColor()) &&
                        board.getPieceAt(endX, endY) == null) {
                    captures.add(new int[]{endX, endY});
                }
            }
        }

        return captures;
    }



    private void highlightPossibleMoves(JPanel boardPanel) {
        Component[] tiles = boardPanel.getComponents();
        for (int i = 0; i < tiles.length; i++) {
            int row = i / board.getSize();
            int col = i % board.getSize();

            JPanel tile = (JPanel) tiles[i];


            for (int[] move : possibleMoves) {
                if (move[0] == row && move[1] == col) {
                    tile.setBackground(Color.RED);
                    break;
                }
            }
        }
    }


    private void highlightCaptureMoves(JPanel boardPanel) {
        Component[] tiles = boardPanel.getComponents();
        for (int i = 0; i < tiles.length; i++) {
            int row = i / board.getSize();
            int col = i % board.getSize();

            JPanel tile = (JPanel) tiles[i];


            for (int[] capture : captureMoves) {
                if (capture[0] == row && capture[1] == col) {
                    tile.setBackground(Color.GREEN);
                    break;
                }
            }
        }
    }
}
