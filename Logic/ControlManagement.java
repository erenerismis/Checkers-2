package Logic;

public class ControlManagement {
    private Board board;
    private Player player1;
    private Player player2;
    private GameRules gameRules;

    public ControlManagement(Board board, Player player1, Player player2, GameRules gameRules) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.gameRules = gameRules;
    }

    public boolean isMoveValid(int startX, int startY, int endX, int endY) {
        return gameRules.isValidMove(board.getBoard(), startX, startY, endX, endY);
    }

    public void makeMove(int startX, int startY, int endX, int endY) {
        gameRules.makeMove(board.getBoard(), startX, startY, endX, endY);
    }

    public Player getCurrentPlayer() {
        return player1.isTurn() ? player1 : player2;
    }

    public void switchTurn() {
        player1.setTurn(!player1.isTurn());
        player2.setTurn(!player2.isTurn());
    }

    public boolean isGameOver() {
        return (player1.getCapturedPieces() == (board.getBoardSize() * board.getBoardSize()) / 2) ||
                (player2.getCapturedPieces() == (board.getBoardSize() * board.getBoardSize()) / 2);
    }
}
