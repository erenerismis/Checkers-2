//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PlayerTimer extends JPanel {
    private int timeInSeconds;
    private Timer timer;
    private JLabel label;
    private boolean endTurn;

    public PlayerTimer(int initialTimeInSeconds) {
        this.timeInSeconds = initialTimeInSeconds;
        this.label = new JLabel();
        this.add(this.label);
        this.endTurn = false;
        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlayerTimer.this.updateTime();
            }
        });
        this.timer.start();
    }

    private void updateTime() {
        if (this.timeInSeconds > 0) {
            --this.timeInSeconds;
            int minutes = this.timeInSeconds / 60;
            int seconds = this.timeInSeconds % 60;
            this.label.setText(String.format("%02d:%02d", minutes, seconds));
        } else {
            this.label.setText("Time's up!");
            this.timer.stop();
            this.endTurn = true;
        }

    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
        if (endTurn) {
            this.timer.stop();
        } else {
            this.timer.start();
        }

    }

    public boolean isEndTurn() {
        return this.endTurn;
    }
}

