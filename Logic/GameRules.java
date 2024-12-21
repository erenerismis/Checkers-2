package Logic;

import java.util.ArrayList;
import java.util.List;

public class GameRules {

    private int boardSize;

    public GameRules(int boardSize) {
        this.boardSize = boardSize;
    }

    public boolean isValidMove(int[][] board, int startX, int startY, int endX, int endY) {
        if (endX < 0 || endX >= boardSize || endY < 0 || endY >= boardSize || board[endX][endY] != 0) {
            return false;
        }

        int piece = board[startX][startY];
        int dx = endX - startX;
        int dy = endY - startY;

        if (Math.abs(dx) == 1 && Math.abs(dy) == 1) {
            return (piece == 1 && dx == -1) || (piece == 2 && dx == 1) || isKing(piece);
        }

        if (Math.abs(dx) == 2 && Math.abs(dy) == 2) {
            int midX = startX + dx / 2;
            int midY = startY + dy / 2;
            return isCaptureMove(board, startX, startY, endX, endY, midX, midY);
        }

        return false;
    }

    public boolean isCaptureMove(int[][] board, int startX, int startY, int endX, int endY, int midX, int midY) {
        int piece = board[startX][startY];
        int opponent = (piece == 1) ? 2 : 1;
        return board[midX][midY] == opponent && board[endX][endY] == 0;
    }

    public void makeMove(int[][] board, int startX, int startY, int endX, int endY) {
        int piece = board[startX][startY];
        board[startX][startY] = 0;
        board[endX][endY] = piece;

        if (Math.abs(startX - endX) == 2) {
            int midX = (startX + endX) / 2;
            int midY = (startY + endY) / 2;
            board[midX][midY] = 0;
        }

        if (isKingPromotion(piece, endX)) {
            board[endX][endY] = piece + 2;
        }
    }

    public boolean isKingPromotion(int piece, int x) {
        return (piece == 1 && x == 0) || (piece == 2 && x == boardSize - 1);
    }

    public boolean isKing(int piece) {
        return piece == 3 || piece == 4;
    }


}
