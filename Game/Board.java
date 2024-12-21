package Game;

public class Board {
    private final int size;
    private Piece[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new Piece[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i + j) % 2 != 0) {
                    if (i < 3) {
                        grid[i][j] = new Piece("Black", false);
                    } else if (i > size - 4) {
                        grid[i][j] = new Piece("White", false);
                    }
                }
            }
        }
    }

    public Piece getPieceAt(int x, int y) {
        return grid[x][y];
    }

    public void movePiece(int startX, int startY, int endX, int endY) {
        grid[endX][endY] = grid[startX][startY];
        grid[startX][startY] = null;
    }

    public void removePiece(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            grid[x][y] = null; // Taşı tahtadan kaldır
            System.out.println("Piece removed from (" + x + ", " + y + ")");
        } else {
            System.out.println("Invalid coordinates for removing piece: (" + x + ", " + y + ")");
        }
    }



    public int getSize() {
        return size;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(grid[i][j].getColor().charAt(0) + " ");
                }
            }
            System.out.println();
        }
    }

}