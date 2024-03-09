package com.mycompany.brick.breaker.game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

// Define the MapGenerator class
public class MapGenerator {
    // Declare member variables
    public int map[][];
    public int bricksWidth;
    public int bricksHeight;

    // Constructor to initialize map and set brick dimensions
    public MapGenerator(int row, int col) {
        // Initialize the map array with given row and column dimensions
        map = new int[row][col];
        
        // Initialize all elements of the map array to 1
        for (int[] map1 : map) {
            for (int j = 0; j < map[0].length; j++) {
                map1[j] = 1;
            }
        }
        
        // Calculate the width of each brick
        bricksWidth = 540 / col;
        // Calculate the height of each brick
        bricksHeight = 150 / row;
    }

    // Method to draw the map
    public void draw(Graphics2D g) {
        // Iterate over each element of the map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // If the value at the current position is greater than 0
                if (map[i][j] > 0) {
                    // Set the color and fill a rectangle representing the brick
                    g.setColor(Color.red);
                    g.fillRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);

                    // Set the stroke and draw a rectangle around the brick
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);
                }
            }
        }
    }

    // Method to set the value of a brick at a specific position
    public void setBricksValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
