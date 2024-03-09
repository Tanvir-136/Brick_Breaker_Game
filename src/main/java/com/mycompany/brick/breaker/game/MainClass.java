package com.mycompany.brick.breaker.game;
import javax.swing.JFrame;

public class MainClass {
    public static void main(String[] args) {
       JFrame obj =new JFrame();
       GamePlay gamePlay = new GamePlay();
       obj.setBounds(10,10,800,700);
       obj.setTitle("Brick Breaker");
       obj.setResizable(false);
       obj.setVisible(true);
       obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       obj.add(gamePlay);  
    }
}