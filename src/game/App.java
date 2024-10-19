package game;

import javax.swing.*;
import ui.SnakeGame;
import ui.ScorePanel;
import java.awt.*;

public class App {
    public static void main(String[] args) {

        int boardWidth = 600;
        int boardHeight = 600;

        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());  

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        
        ScorePanel scorePanel = new ScorePanel();
        
        frame.add(scorePanel, BorderLayout.WEST);  
        frame.add(snakeGame, BorderLayout.CENTER); 

        frame.pack();
        frame.setLocationRelativeTo(null); 
        frame.setResizable(false);
        frame.setVisible(true);

        Timer timer = new Timer(100, e -> {
            scorePanel.setScore(snakeGame.getScore());
            if (snakeGame.isGameOver()) {
                scorePanel.setGameOver(true);
            }
        });
        timer.start();
    }
}
