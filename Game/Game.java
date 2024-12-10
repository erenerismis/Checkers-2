package Game;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;

    public Game(int boardSize, String player1Name, String player2Name) {
        this.board = new Board(boardSize);
        this.player1 = new Player(player1Name, "black");
        this.player2 = new Player(player2Name, "white");
    }

    public void start() {
        System.out.println("Welcome to Checkers!");
        System.out.println(player1.getName() + " (Black) vs " + player2.getName() + " (White)");
        board.printBoard();
    }
}
