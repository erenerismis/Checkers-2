package GUI;

public class GameDisplay {

    private CheckersGameGui gameGui;
    private CheckersBoardGui boardGui;

    public GameDisplay() {
        // Initialize CheckersGameGui and CheckersBoardGui
        this.gameGui = new CheckersGameGui();
        this.boardGui = new CheckersBoardGui();
    }

    // Method to update the board display
    public void updateBoard() {
        // Logic to refresh or update the board display
    }

    // Method to reset the game (reset board, timers, etc.)
    public void resetGame() {
        // Reset the board and timers here
        boardGui.repaint(); // Redraw the board
    }
}

