package com.mycompany.brick.breaker.game;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 0;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 350;
    private int ballposY = 530;
    private int ballXdir = -2;
    private int ballYdir = -5;
    private MapGenerator map;
    

    public GamePlay() {
        // Create a new map for the first level
        map = new MapGenerator(3, 7); // Change parameters for new level
        totalbricks = map.totalBricks(); // Update totalbricks
        addKeyListener(this);
        setFocusable(true);
        Timer = new Timer(delay, this);
        Timer.start();
    }
    
    @Override
    public void paint(Graphics g) {
        // Draw the background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        // Draw the map
        map.draw((Graphics2D) g);
        
        // Draw the borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        // Draw the score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Score: " + score, 590, 30);

        // Draw the player paddle
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);

         // Draw the ball
        g.setColor(Color.GREEN);
        g.fillOval(ballposX, ballposY, 20, 20);

        
        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart!", 190, 340);
        }

        //!!~Game Win~!!
        if(totalbricks == 0){
        // Set play to false and stop the ball
            play = false;
            ballYdir = -2;
            ballXdir = -1;
    
            // Display game win message
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("      You Win!   ", 190, 300);
    
            // Display score message
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("          Score:  " + score, 190, 340); 
            
            // Display press Enter to Restart message
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart!", 190, 380);
        }
        g.dispose();
    }

    // Method to handle action events
    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();
        if (play) {
            // Collision detection with bar
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }
            // Check for collisions with bricks
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        // Calculate brick position and size
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;
                        
                        // Create rectangles for ball and brick
                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;
                        // Handle collisions
                        if (ballrect.intersects(brickrect)) {
                            // Remove brick
                            map.setBricksValue(0, i, j);
                            totalbricks--;
                            score += 10;
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            
            // Move the ball
            ballposX += ballXdir;
            ballposY += ballYdir;
            
            // Check for wall collisions
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
            if (totalbricks == 0) {
            // Proceed to the next level
                if (map.map.length == 4 && map.map[0].length == 12) {
                    play = false;
                } else {
                    map = new MapGenerator(4, 12);
                    initNewLevel(4, 12);
                }
            }
        repaint();
    }
}
    @Override
    public void keyTyped(KeyEvent e) {

       }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        // Restart the game when pressed Enter key
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -2;
                ballYdir = -5;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);
                repaint();
            }
        }
    }
    //Method to moveLeft and moveRight
    public void moveRight (){
        play = true;
        playerX += 20;
    }
    public void moveLeft (){
        play = true;
        playerX -= 20;
    }
     // Add a method to initialize a new level
    public void initNewLevel(int rows, int cols) {
        map = new MapGenerator(rows, cols); 
        totalbricks = map.totalBricks(); 
        resetPlayer(); 
        resetBall(); 
        score = 0; 
        play = true; 
    }

    // Method to reset player position
    private void resetPlayer() {
        playerX = 310; 
    }

    // Method to reset ball position and direction
    private void resetBall() {
        ballposX = 350; 
        ballposY = 530; 
        ballXdir = -2; 
        ballYdir = -5;     
    }
}