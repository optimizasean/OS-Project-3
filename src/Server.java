//Operating Systems Project (osp) package
package osp;

//Frames, Panes, and Panels
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//Layouts and Constraints
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

//Labels and Text
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

//Color
import java.awt.Color;



public class Server extends JPanel {
    /**************************
     * Define Swing variables
     **************************/
    private JScrollPane serverLogPane = null;
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
        //Set base panel background
        this.setBackground(Color.GRAY);

        //Set base panel layout
        base = new GridBagLayout();
        this.setLayout(base);

        //Prepare constraints object for GridBagLayout
        baseConstraints = new GridBagConstraints();

        //Label for top of base panel
        windowLabel = new JLabel("SERVER");
        baseConstraints.gridx = 1;
        baseConstraints.gridy = 0;
        baseConstraints.gridwidth = 2;
        baseConstraints.gridheight = 1;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0.5;
        baseConstraints.weighty = 0.5;
        //baseConstraints.insets = null;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(windowLabel, baseConstraints);

        //Log for server on center of panel
        serverLog = new JTextArea();
        serverLogPane = new JScrollPane(serverLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        serverLogPane.setBackground(Color.WHITE);
        baseConstraints.gridx = 0;
        baseConstraints.gridy = 1;
        baseConstraints.gridwidth = 4;
        baseConstraints.gridheight = 4;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0.5;
        baseConstraints.weighty = 0.5;
        //baseConstraints.insets = null;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(serverLogPane, baseConstraints);

        //Label for port under server log
        portLabel = new JLabel("Port:");
        baseConstraints.gridx = 1;
        baseConstraints.gridy = 5;
        baseConstraints.gridwidth = 1;
        baseConstraints.gridheight = 1;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0.5;
        baseConstraints.weighty = 0.5;
        //baseConstraints.insets = null;
        baseConstraints.anchor = GridBagConstraints.LINE_END;
        baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(portLabel, baseConstraints);

        //Field for taking port abot start button
        portField = new JTextField("9001");
        baseConstraints.gridx = 2;
        baseConstraints.gridy = 5;
        baseConstraints.gridwidth = 1;
        baseConstraints.gridheight = 1;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0.5;
        baseConstraints.weighty = 0.5;
        //baseConstraints.insets = null;
        baseConstraints.anchor = GridBagConstraints.LINE_START;
        baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(portField, baseConstraints);

        //Button to start server
        startButton = new JButton("START");
        baseConstraints.gridx = 1;
        baseConstraints.gridy = 6;
        baseConstraints.gridwidth = 2;
        baseConstraints.gridheight = 1;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0.5;
        baseConstraints.weighty = 0.5;
        //baseConstraints.insets = null;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(startButton, baseConstraints);

        //Button to stop server under start button
        stopButton = new JButton("STOP");
        baseConstraints.gridx = 1;
        baseConstraints.gridy = 7;
        baseConstraints.gridwidth = 2;
        baseConstraints.gridheight = 1;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0.5;
        baseConstraints.weighty = 0.5;
        //baseConstraints.insets = null;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(stopButton, baseConstraints);

        //Button to force quit on bottom
        quitButton = new JButton("QUIT");
        baseConstraints.gridx = 0;
        baseConstraints.gridy = 8;
        baseConstraints.gridwidth = 4;
        baseConstraints.gridheight = 1;
        baseConstraints.ipadx = 0;
        baseConstraints.ipady = 0;
        baseConstraints.weightx = 0.5;
        baseConstraints.weighty = 0.5;
        //baseConstraints.insets = null;
        baseConstraints.anchor = GridBagConstraints.CENTER;
        baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(quitButton, baseConstraints);
    }
}
