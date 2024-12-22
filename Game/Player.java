package Game;

public class Player {
    private String name;
    private String color;
    private int wins;
    private int piecesCaptured;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.wins = 0;
        this.piecesCaptured = 0;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.wins++;
    }

    public int getPiecesCaptured() {
        return piecesCaptured;
    }

    public void addCapturedPiece() {
        this.piecesCaptured++;
    }

    public void resetCapturedPieces() {
        this.piecesCaptured = 0;
    }
}