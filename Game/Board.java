package Game;

public class Board {
    private int size;
    private Piece[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new Piece[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 0 || row == size - 1) {
                    grid[row][col] = null;
                } else if (row == 1 || row == 2) {
                    grid[row][col] = new Piece("black");
                } else if (row == size - 2 || row == size - 3) {
                    grid[row][col] = new Piece("white");
                } else {
                    grid[row][col] = null;
                }
            }
        }
    }

    public Piece[][] getGrid() {
        return grid;
    }

    public void printBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(grid[row][col].getColor().charAt(0) + " ");
                }
            }
            System.out.println();
        }
    }
}
