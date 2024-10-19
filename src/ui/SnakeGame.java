package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
	
	private class Tile {
		int x;
		int y;
		
		Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	int boardWidth;
	int boardHeight;
	int tileSize = 25;
	
	//Snake
	Tile snakeHead;
	ArrayList<Tile> snakeBody;
	
	//Food
	Tile food;
	Random random;
	
	//game logic
	Timer gameLoop;
	int velocityX;
	int velocityY;
	boolean gameOver = false;
	
	public SnakeGame(int boardWidth, int boardHeight) {
	    int borderWidth = 10;  
	    this.boardWidth = boardWidth - 2 * borderWidth; 
	    this.boardHeight = boardHeight - 2 * borderWidth; 
	    setPreferredSize(new Dimension(boardWidth, boardHeight)); 
	    setBackground(Color.black);
	    addKeyListener(this);
	    setFocusable(true);

	    snakeHead = new Tile(5, 5);
	    snakeBody = new ArrayList<>();

	    food = new Tile(10, 10);
	    random = new Random();
	    placeFood();

	    velocityX = 0;
	    velocityY = 0;

	    gameLoop = new Timer(100, this);
	    gameLoop.start();
	}

	
	public int getScore() {
		return snakeBody.size();
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    Color borderColor = Color.GRAY;

	    int borderWidth = 10;

	    g.setColor(borderColor);
	    g.fillRect(0, 0, getWidth(), getHeight());

	    g.setColor(Color.black); 
	    g.fillRect(borderWidth, borderWidth, boardWidth, boardHeight);

	    drawBackground(g, borderWidth); 
	    draw(g, borderWidth);  
	}

	public void drawBackground(Graphics g, int offset) {
	    Color mediumGreen = new Color(34, 139, 34);
	    Color darkGreen = new Color(0, 100, 0); 
	    
	    for (int row = 0; row < boardHeight / tileSize; row++) {
	        for (int col = 0; col < boardWidth / tileSize; col++) {
	            if ((row + col) % 2 == 0) {
	                g.setColor(mediumGreen);
	            } else {
	                g.setColor(darkGreen);
	            }
	            g.fillRect(offset + col * tileSize, offset + row * tileSize, tileSize, tileSize);
	        }
	    }
	}


	 
	public void draw(Graphics g, int offset) {
	    // Food
	    g.setColor(Color.red);
	    g.fillOval(offset + food.x * tileSize, offset + food.y * tileSize, tileSize, tileSize);
	    
	    // Snake Head
	    g.setColor(Color.blue);
	    g.fill3DRect(offset + snakeHead.x * tileSize, offset + snakeHead.y * tileSize, tileSize, tileSize, true);
	    
	    // Snake Body
	    for (int i = 0; i < snakeBody.size(); i++) {
	        Tile snakePart = snakeBody.get(i);
	        g.fill3DRect(offset + snakePart.x * tileSize, offset + snakePart.y * tileSize, tileSize, tileSize, true);
	    }
	}




	public void placeFood() {
		food.x = random.nextInt(boardWidth / tileSize);
		food.y = random.nextInt(boardHeight / tileSize);
	}

	public boolean collision(Tile tile1, Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y;
	}
	
	public void move() {
		//eat food
		if (collision(snakeHead, food)) {
			snakeBody.add(new Tile(food.x, food.y));
			placeFood();
		}
		
		//Snake Body
		for (int i = snakeBody.size() - 1; i >= 0; i--) {
			Tile snakePart = snakeBody.get(i);
			if (i == 0) {
				snakePart.x = snakeHead.x;
				snakePart.y = snakeHead.y;
			} 
			else {
				Tile prevSnakePart = snakeBody.get(i-1);
				snakePart.x = prevSnakePart.x;
				snakePart.y = prevSnakePart.y;
			}
		}
		
		//Snake Head
		snakeHead.x += velocityX;
		snakeHead.y += velocityY;
		
		//game over conditions
		for (int i = 0; i < snakeBody.size(); i++) {
			Tile snakePart = snakeBody.get(i);
			
			if (collision(snakeHead, snakePart)) {
				gameOver = true;
			}
		}
		
		if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
			snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardWidth) {
			gameOver = true;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if (gameOver) {
			gameLoop.stop();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
			velocityX = 0;
			velocityY = -1;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
			velocityX = 0;
			velocityY = 1;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
			velocityX = -1;
			velocityY = 0;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
			velocityX = 1;
			velocityY = 0;
		}
	}
	
	//do not need
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
} 
