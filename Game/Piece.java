package Game;

public class Piece {
    private String color;
    private boolean isKing;

    public Piece(String color) {
        this.color = color;
        this.isKing = false;
    }

    public void promoteToKing() {
        this.isKing = true;
    }

    public String getColor() {
        return color;
    }

    public boolean isKing() {
        return isKing;
    }
}
