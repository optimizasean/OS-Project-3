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

//Events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Server
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;


/********************************
 * Server class is used to start
 * server and manage client
 * connections as well as work with
 * the GUI as a panel to show things
 ********************************/
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

    //Logger
    LocalLogger logger = null;

    //Server
    private ServerSocket server = null;
	private Socket client = null;
    private int port = 0;
    
    //Message passing
    

    public Server() {
        this.GUI();
    }

    public void getPort() {
        this.port = Integer.parseInt(this.portField.getText());
        if (this.port < 0 || this.port > 65535) {
            this.port = 9001;
            this.portField.setText("9001");
        }
        return;
    }
    
    public void run() {
        try {
            server = new ServerSocket(port);
        } catch (IOException iex) {
            System.out.println("Failed to start server");
        }
        
        try {
            while(true) {
                client = server.accept();
                Thread t = new ServerThread(client);
                t.start();
            }
        } catch (IOException iex) {}
    }

    private void GUI() {
        this.GUIVisual();
        this.GUIFunctional();

        return;
    }
    private void GUIVisual() {
        //Set base panel background
        this.setBackground(Color.GRAY);

        //Set base panel layout
        base = new GridBagLayout();
        this.setLayout(base);

        //Prepare constraints object for GridBagLayout
        this.baseConstraints = new GridBagConstraints();

        //Label for top of base panel
        this.windowLabel = new JLabel("SERVER");
        this.baseConstraints.gridx = 1;
        this.baseConstraints.gridy = 0;
        this.baseConstraints.gridwidth = 2;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 0.5;
        this.baseConstraints.weighty = 0.5;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(windowLabel, baseConstraints);

        //Log for server on center of panel
        this.serverLog = new JTextArea();
        this.serverLogPane = new JScrollPane(serverLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.serverLogPane.setBackground(Color.WHITE);
        this.baseConstraints.gridx = 0;
        this.baseConstraints.gridy = 1;
        this.baseConstraints.gridwidth = 4;
        this.baseConstraints.gridheight = 4;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 0.5;
        this.baseConstraints.weighty = 0.5;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(serverLogPane, baseConstraints);

        //Label for port under server log
        this.portLabel = new JLabel("Port:");
        this.baseConstraints.gridx = 1;
        this.baseConstraints.gridy = 5;
        this.baseConstraints.gridwidth = 1;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 0.5;
        this.baseConstraints.weighty = 0.5;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.LINE_END;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(portLabel, baseConstraints);

        //Field for taking port abot start button
        this.portField = new JTextField("9001");
        this.baseConstraints.gridx = 2;
        this.baseConstraints.gridy = 5;
        this.baseConstraints.gridwidth = 1;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 0.5;
        this.baseConstraints.weighty = 0.5;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.LINE_START;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(portField, baseConstraints);

        //Button to start server
        this.startButton = new JButton("START");
        this.baseConstraints.gridx = 1;
        this.baseConstraints.gridy = 6;
        this.baseConstraints.gridwidth = 2;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 0.5;
        this.baseConstraints.weighty = 0.5;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(startButton, baseConstraints);

        //Button to stop server under start button
        this.stopButton = new JButton("STOP");
        this.baseConstraints.gridx = 1;
        this.baseConstraints.gridy = 7;
        this.baseConstraints.gridwidth = 2;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 0.5;
        this.baseConstraints.weighty = 0.5;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(stopButton, baseConstraints);

        //Button to force quit on bottom
        this.quitButton = new JButton("QUIT");
        this.baseConstraints.gridx = 0;
        this.baseConstraints.gridy = 8;
        this.baseConstraints.gridwidth = 4;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 0.5;
        this.baseConstraints.weighty = 0.5;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(quitButton, baseConstraints);

        return;
    }
    private void GUIFunctional() {
        this.startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("START BUTTON PUSHED");
            }
        });

        this.stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("STOP BUTTON PUSHED");
            }
        });

        this.quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("QUIT BUTTON PUSHED");
                System.exit(0);
            }
        });

        return;
    }
}
