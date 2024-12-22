package GUI;

import Game.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoWindow extends JFrame {
    private Player player1;
    private Player player2;

    private JLabel player1NameLabel;
    private JLabel player1WinsLabel;
    private JLabel player1CapturedLabel;

    private JLabel player2NameLabel;
    private JLabel player2WinsLabel;
    private JLabel player2CapturedLabel;

    public PlayerInfoWindow(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        setTitle("Player Info");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Pencereyi kapatınca tamamen kapanmasın
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));

        // Player 1 Panel
        JPanel player1Panel = new JPanel(new GridLayout(3, 1));
        player1Panel.setBorder(BorderFactory.createTitledBorder("Player 1"));
        player1NameLabel = new JLabel("Name: " + player1.getName());
        player1WinsLabel = new JLabel("Wins: " + player1.getWins());
        player1CapturedLabel = new JLabel("Captured: " + player1.getPiecesCaptured());
        player1Panel.add(player1NameLabel);
        player1Panel.add(player1WinsLabel);
        player1Panel.add(player1CapturedLabel);

        // Player 2 Panel
        JPanel player2Panel = new JPanel(new GridLayout(3, 1));
        player2Panel.setBorder(BorderFactory.createTitledBorder("Player 2"));
        player2NameLabel = new JLabel("Name: " + player2.getName());
        player2WinsLabel = new JLabel("Wins: " + player2.getWins());
        player2CapturedLabel = new JLabel("Captured: " + player2.getPiecesCaptured());
        player2Panel.add(player2NameLabel);
        player2Panel.add(player2WinsLabel);
        player2Panel.add(player2CapturedLabel);

        infoPanel.add(player1Panel);
        infoPanel.add(player2Panel);
        add(infoPanel, BorderLayout.CENTER);

        // Kontrol Butonları: Draw ve Surrender
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton drawButton = new JButton("Draw");
        drawButton.addActionListener(e -> handleDraw());

        JButton surrenderButton = new JButton("Surrender");
        surrenderButton.addActionListener(e -> handleSurrender());

        buttonPanel.add(drawButton);
        buttonPanel.add(surrenderButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateInfo() {
        // Player 1 bilgilerini güncelle
        player1NameLabel.setText("Name: " + player1.getName());
        player1WinsLabel.setText("Wins: " + player1.getWins());
        player1CapturedLabel.setText("Captured: " + player1.getPiecesCaptured());

        // Player 2 bilgilerini güncelle
        player2NameLabel.setText("Name: " + player2.getName());
        player2WinsLabel.setText("Wins: " + player2.getWins());
        player2CapturedLabel.setText("Captured: " + player2.getPiecesCaptured());
    }

    private void handleDraw() {
        int confirm = JOptionPane.showConfirmDialog(this, "Do both players agree to a draw?");
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "The game ended in a draw!");
            System.exit(0);
        }
    }

    private void handleSurrender() {
        String winner = JOptionPane.showInputDialog(this, "Who is surrendering? (1 for Player 1, 2 for Player 2)");
        if ("1".equals(winner)) {
            JOptionPane.showMessageDialog(this, player2.getName() + " wins! Player 1 surrendered.");
        } else if ("2".equals(winner)) {
            JOptionPane.showMessageDialog(this, player1.getName() + " wins! Player 2 surrendered.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter 1 or 2.");
            return;
        }
        System.exit(0);
    }
}