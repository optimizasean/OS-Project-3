package osp;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Server extends JPanel {
    /**************************
     * Define Swing variables
     **************************/
    private GridBagLayout base = null;
    private GridBagConstraints baseConstraints = null;
    private JLabel windowLabel = null;
    private JLabel portLabel = null;
    private JTextField portField = null;
    private JTextArea serverLog = null;
    private JButton startButton = null;
    private JButton stopButton = null;
    private JButton quitButton = null;

    public Server() {
        base = new GridBagLayout();
        pane.setLayout(base);

        baseConstraints = new GridBagConstraints();

        windowLabel = new JLabel("SERVER");
        baseConstraints.gridx = 1;
        baseConstraints.gridy = 0;
        baseConstraints.gridWidth = 2;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0;
        baseConstraints.weighty = 0;
        baseConstraints.insets = 0;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        pane.add(windowLabel, baseConstraints);

        serverLog = new JTextArea();
        baseConstraints.gridx = 0;
        baseConstraints.gridy = 1;
        baseConstraints.gridwidth = 4;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0;
        baseConstraints.weighty = 0;
        baseConstraints.insets = 0;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        pane.add(serverLog, baseConstraints);

        portLabel = new JLabel("Port:");
        baseConstraints.gridx = 1;
        baseConstraints.gridy = 2;
        baseConstraints.gridwidth = 1;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0;
        baseConstraints.weighty = 0;
        baseConstraints.insets = 0;
        baseConstraints.anchor = GridBagConstraints.RIGHT;
        baseConstraints.fill = GridBagConstraints.BOTH;
        pane.add(portLabel, baseConstraints);

        portField = new JTextField("9001");
        baseConstraints.gridx = 2;
        baseConstraints.gridy = 2;
        baseConstraints.gridwidth = 1;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0;
        baseConstraints.weighty = 0;
        baseConstraints.insets = 0;
        baseConstraints.anchor = GridBagConstraints.LEFT;
        baseConstraints.fill = GridBagConstraints.BOTH;
        pane.add(portField, baseConstraints);

        startButton = new JButton("START");
        baseConstraints.gridx = 1;
        baseConstraints.gridy = 3;
        baseConstraints.gridwidth = 2;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0;
        baseConstraints.weighty = 0;
        baseConstraints.insets = 0;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        pane.add(startButton, baseConstraints);

        stopButton = new JButton("STOP");
        baseConstraints.gridx = 1;
        baseConstraints.gridy = 4;
        baseConstraints.gridwidth = 2;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0;
        baseConstraints.weighty = 0;
        baseConstraints.insets = 0;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        pane.add(stopButton, baseConstraints);

        quitButton = new JButton("QUIT");
        baseConstraints.gridx = 0;
        baseConstraints.gridy = 5;
        baseConstraints.gridwidth = 4;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0;
        baseConstraints.weighty = 0;
        baseConstraints.insets = 0;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
    }
}