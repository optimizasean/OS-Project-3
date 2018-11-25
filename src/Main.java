package osp;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.shape.Box;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;

import com.sun.javafx.scene.paint.GradientUtils;


public class Main {
    private static JFrame frame = null;
    private static JPanel mainPanel = null;
    private static JPanel clientsPanel = null;
    private static FlowLayout mainLayout = null;

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
        mainPanel.setBackground(Color.BLACK);
        mainLayout = new FlowLayout();
        mainPanel.setLayout(mainLayout);
        
        // creating 2 panels for left and right
        clientsPanel = new JPanel();
        clientsPanel.setBackground(Color.DARK_GRAY);
        clientsPanel.setLayout(new BoxLayout(clientsPanel, BoxLayout.Y_AXIS));
        mainPanel.add(clientsPanel);

        //ADD SERVER HERE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        JPanel right = new JPanel(); 
        right.setBackground(Color.BLUE);
        mainPanel.add(right);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);
    }
    
    
}

