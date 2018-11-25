package javaSwing;


import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.Box;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;

import com.sun.javafx.scene.paint.GradientUtils;


public class Main {
    private static JFrame frame = null;
    private static JPanel mainPanel = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater (new Runnable() {
            public void run() {
                GUI();
            }
        });
    }

    private static void GUI() {
        frame = new JFrame("OSP3");
        mainPanel = new JPanel();
        
        /******
         * mainPanel.setLayout(STUFF);
         * mainPanel.add(STUFF);
         */
        // setting up flow layout for left and right
        mainPanel.setLayout(new FlowLayout());
        
        // creating 2 panels for left and right
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.GRAY);

        JPanel right = new JPanel(); 
        right.setBackground(Color.WHITE);


        // add the panel for left and right in there
        mainPanel.add(left);
        mainPanel.add(right);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);
    }
    
    
}

