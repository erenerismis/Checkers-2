package GUI;

import javax.swing.*;
import java.awt.*;

public class CheckersGameGui {

    public CheckersGameGui() {
        JFrame frame = new JFrame("Checkers 2: Custom Game");
        frame.setLayout(new BorderLayout());

        // Create the checkers board
        JPanel checkersPanel = new JPanel(new FlowLayout(1));
        CheckersBoardGui board = new CheckersBoardGui();
        checkersPanel.add(board);

        // Create player timers
        PlayerTimer player1Timer = new PlayerTimer(300);
        PlayerTimer player2Timer = new PlayerTimer(300);

        // Create panels for the players
        JPanel player1Panel = createPlayerPanel("Player 1's Timer", player1Timer);
        JPanel player2Panel = createPlayerPanel("Player 2's Timer", player2Timer);

        // Create move count panel
        JLabel player1MoveCountLabel = new JLabel("Move Count: 0", 0);
        JLabel player2MoveCountLabel = new JLabel("Move Count: 0", 0);
        JPanel moveCountPanel = new JPanel(new GridLayout(2, 1));
        moveCountPanel.add(player1MoveCountLabel);
        moveCountPanel.add(player2MoveCountLabel);

        // Adding components to the frame
        frame.add(player1Panel, "West");
        frame.add(moveCountPanel, "Center");
        frame.add(player2Panel, "East");
        frame.add(checkersPanel, "South");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static JPanel createPlayerPanel(String labelText, PlayerTimer playerTimer) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText, 0);
        panel.add(label, "North");
        panel.add(playerTimer, "Center");
        return panel;
    }

    public static void main(String[] args) {
        new CheckersGameGui();
    }
}

