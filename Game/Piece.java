ackage Game;

public class Piece {
    private String color;
    private boolean isKing;

    public Piece(String color, boolean isKing) {
        this.color = color;
        this.isKing = isKing;
    }

    public String getColor() {
        return color;
    }

    public boolean isKing() {
        return isKing;
    }

    public void promoteToKing() {
        this.isKing = true;
    }
}