package Logic;


import Game.Board;
import Game.Piece;
public class GameRules {


        public boolean isValidMove(Board board, int startX, int startY, int endX, int endY) {
            Piece piece = board.getPieceAt(startX, startY);
            if (piece == null) return false;

            int deltaX = Math.abs(endX - startX);
            int deltaY = Math.abs(endY - startY);

            if (piece.isKing()) {
                if (deltaX == deltaY) {
                    int dirX = (endX - startX) > 0 ? 1 : -1;
                    int dirY = (endY - startY) > 0 ? 1 : -1;
                    int currentX = startX + dirX;
                    int currentY = startY + dirY;

                    boolean enemyFound = false;

                    while (currentX != endX && currentY != endY) {
                        Piece currentPiece = board.getPieceAt(currentX, currentY);
                        if (currentPiece != null) {
                            if (enemyFound || currentPiece.getColor().equals(piece.getColor())) {
                                return false;
                            }
                            enemyFound = true;
                        }
                        currentX += dirX;
                        currentY += dirY;
                    }

                    if (enemyFound && board.getPieceAt(endX, endY) == null) {
                        return true;
                    }

                    return !enemyFound && board.getPieceAt(endX, endY) == null;
                }
            }

            if ((piece.getColor().equals("White") && startX > endX) ||
                    (piece.getColor().equals("Black") && startX < endX)) {
                if (deltaX == 1 && deltaY == 1) {
                    return board.getPieceAt(endX, endY) == null;
                } else if (deltaX == 2 && deltaY == 2) {
                    int midX = (startX + endX) / 2;
                    int midY = (startY + endY) / 2;
                    Piece midPiece = board.getPieceAt(midX, midY);
                    return midPiece != null && !midPiece.getColor().equals(piece.getColor()) &&
                            board.getPieceAt(endX, endY) == null;
                }
            }

            return false;
        }

        public boolean hasMandatoryCapture(Board board, String playerColor) {
            int size = board.getSize();
            boolean captureFound = false;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Piece piece = board.getPieceAt(i, j);

                    if (piece != null && piece.getColor().equals(playerColor)) {
                        if (canCapture(board, i, j)) {
                            System.out.println("DEBUG: Piece at (" + i + ", " + j + ") can capture.");
                            captureFound = true;
                        }
                    }
                }
            }

            if (!captureFound) {
                System.out.println("DEBUG: No mandatory captures for " + playerColor);
            }

            return captureFound;
        }

        public boolean canCapture(Board board, int startX, int startY) {
            Piece piece = board.getPieceAt(startX, startY);
            if (piece == null) return false;

            int[][] directions;

            if (piece.isKing()) {
                directions = new int[][] {{-2, -2}, {-2, 2}, {2, -2}, {2, 2}};
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
                        return true;
                    }
                }
            }

            return false;
        }

        public boolean canCaptureAgain(Board board, int startX, int startY) {
            return canCapture(board, startX, startY);
        }

        public boolean shouldPromote(Piece piece, int endX, int boardSize) {
            return (!piece.isKing() && ((piece.getColor().equals("White") && endX == 0) ||
                    (piece.getColor().equals("Black") && endX == boardSize - 1)));
        }
    }

