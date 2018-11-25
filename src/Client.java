package osp;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Client extends JPanel {
    /**************************
     * Define Swing variables
     **************************/
    private JPanel readPanel = null;
    private JPanel writePanel = null;
    private JPanel viewPanel = null;
    private BorderLayout base = null;
    private BoxLayout readLayout = null;
    private BoxLayout writeLayout = null;
    private GridLayout viewLayout = null;
    private JLabel windowLabel = null;
    private JLabel readViewLabel = null;
    private JLabel writeViewLabel = null;
    private JLabel readLabel = null;
    private JLabel writeLabel = null;
    private JTextField readNumber = null;
    private JTextField writeNumber = null;
    private JTextArea pcLog = null;
    private JButton readPlus = null;
    private JButton readMinus = null;
    private JButton writePlus = null;
    private Jbutton writeMinus = null;

    public Client(int clientNumber) {
        this.setBackground(Color.GRAY);

        base = new BorderLayout();
        this.setLayout(base);

        windowLabel = new JLabel("PC " + clientNumber);
        this.add(windowLabel, BorderLayout.NORTH);

        readPanel = new JPanel();
        readPanel.setBackground(Color.LIGHT_GRAY);
        readLayout = new BoxLayout();
        readPanel.setLayout(readLayout, BoxLayout.Y_AXIS);
        readLabel = new JLabel("READ");
        readPanel.add(readLabel);
        readPlus = new JButton("+");
        readPanel.add(readPlus);
        readMinus = new JButton("-");
        readPanel.add(readMinus);
        this.add(readPanel, BorderLayout.WEST);

        pcLog = new JTextArea(50, 50);
        this.add(pcLog, BorderLayout.CENTER);

        writePanel = new JPanel();
        writePanel.setBackground(Color.LIGHT_GRAY);
        writeLayout = new BorderLayout();
        writePanel.setLayout(writeLayout, javax.swing.BoxLayout.Y_AXIS);
        writeLabel = new JLabel("WRITE");
        writePanel.add(writeLabel);
        writePlus = new JButton("+");
        writePanel.add(writePlus);
        writeMinus = new JButton("-");
        writePanel.add(writeMinus);
        this.add(writePanel, BorderLayout.EAST);

        viewPanel = new JPanel();
        viewPanel.setBackground(Color.LIGHT_GRAY);
        viewLayout = new GridLayout(2, 2);
        viewPanel.setLayout(viewLayout);
        readViewLabel = new JLabel("Read: %");
        viewPanel.add(readViewLabel);
        readNumber = new JTextField("??");
        viewPanel.add(readNumber);
        writeViewLabel = new JLabel("Write: %");
        viewPanel.add(writeViewLabel);
        writeNumber = new JTextField("??");
        viewPanel.add(writeNumber);
        this.add(viewPanel, BorderLayout.SOUTH);
    }
}