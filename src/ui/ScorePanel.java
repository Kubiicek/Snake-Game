package ui;

import java.awt.*;
import javax.swing.*;

public class ScorePanel extends JPanel {

    private int score;
    private boolean gameOver;

    public ScorePanel() {
        this.score = 0;
        this.gameOver = false;
        setPreferredSize(new Dimension(300, 600));
        setBackground(Color.DARK_GRAY); 
    }

    public void setScore(int score) {
        this.score = score;
        repaint(); 
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        repaint(); 
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        if (gameOver) {
            g.drawString("Game Over", 110, 500);  
        } else {
            g.drawString("Score: " + score, 110, 500); 
        }
    }
}