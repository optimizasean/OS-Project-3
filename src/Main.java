package osp;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;


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

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }
}