package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputHandling {

    private CheckersBoardGui boardGui;

    public InputHandling(CheckersBoardGui boardGui) {
        this.boardGui = boardGui;
        // Add mouse listener for detecting piece clicks and movements
        this.boardGui.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleInput(e);
            }
        });
    }

    // Method to handle input events, like piece selection and movement
    private void handleInput(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Convert mouse coordinates to board tile coordinates (based on tile size)
        int tileX = x / boardGui.getTileSize();
        int tileY = y / boardGui.getTileSize();

        // Handle piece selection and movement here based on the tile coordinates
        System.out.println("Tile clicked: " + tileX + ", " + tileY);
    }
}
