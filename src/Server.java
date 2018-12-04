//Operating Systems Project (osp) package
package osp;

//Frames, Panes, and Panels
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComponent;

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

//Fonts
import java.awt.Font;

//Events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Server
import java.lang.InterruptedException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Files
import java.io.File;

//Utilities
import java.util.Vector;


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
    File localLog = null;

    //Server
    private ServerSocket server = null;
	private Socket client = null;
    public static int port = 0;
    private int id = 1;

    public Server() {
        this.startLogger();
        this.GUI();
    }

    private void startLogger() {
        this.localLog = new File(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_LOCAL_LOG);
        this.logger = new LocalLogger(this.localLog);
    }
    
    public void log(String log) {
        this.serverLog.append(log);
        this.logger.log(log);
        return;
    }

    private void launchServer() {
        try {
            this.refreshPort();
            this.server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for client");
            
            int clientID = 1;
            while(clientID < 6) {
                this.client = null;
                try {
                    this.client = this.server.accept();
                    System.out.println("PC" + this.id + " Accepted");
                    
                    ObjectOutputStream oos = new ObjectOutputStream(this.client.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(this.client.getInputStream());
                    
                    ServerThread st = new ServerThread(this, this.client, this.id, oos, ois);
                    Main.stv.add(st);
                    Thread t = new Thread(st);
                    t.start();
                    this.id++;
                } catch (IOException iex) {
                    System.err.println("ERROR????????");
                } catch(Exception ex) {
                    this.server.close();
                    ex.printStackTrace();
                }
            }
        } catch(IOException iex) {
            System.err.println("ERRORRRRRRRRRRRR");
        }
        return;
    }

    private void launchClient() {
        Client client = null;
        for (int i = 0; i < Constants.NUMBER_OF_CLIENTS; i++) {
            client = Main.cv.get(i);
            client.launch();
        }
        return;  
    }

    public void refreshPort() {
        port = Integer.parseInt(this.portField.getText());
        if (port < 0 || port > 65535) {
            port = 9001;
            this.portField.setText("9001");
        }
        return;
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
        this.changeFont(this.windowLabel, 5);
        this.baseConstraints.gridx = 1;
        this.baseConstraints.gridy = 0;
        this.baseConstraints.gridwidth = 2;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 1.0;
        this.baseConstraints.weighty = 1.0;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(windowLabel, baseConstraints);

        //Log for server on center of panel
        this.serverLog = new JTextArea(38, 40);
        this.changeFont(this.serverLog, -3);
        this.serverLog.setEditable(false);
        this.serverLogPane = new JScrollPane(serverLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.serverLogPane.setBackground(Color.WHITE);
        this.baseConstraints.gridx = 0;
        this.baseConstraints.gridy = 1;
        this.baseConstraints.gridwidth = 4;
        this.baseConstraints.gridheight = 4;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 1.0;
        this.baseConstraints.weighty = 1.0;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(serverLogPane, baseConstraints);

        //Label for port under server log
        this.portLabel = new JLabel("Port:");
        this.changeFont(this.portLabel, 2);
        this.baseConstraints.gridx = 1;
        this.baseConstraints.gridy = 5;
        this.baseConstraints.gridwidth = 1;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 1.0;
        this.baseConstraints.weighty = 1.0;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.LINE_END;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(portLabel, baseConstraints);

        //Field for taking port abot start button
        this.portField = new JTextField("9001");
        this.changeFont(this.portField, 2);
        this.portField.setEditable(true);
        this.baseConstraints.gridx = 2;
        this.baseConstraints.gridy = 5;
        this.baseConstraints.gridwidth = 1;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 1.0;
        this.baseConstraints.weighty = 1.0;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.LINE_START;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(portField, baseConstraints);

        //Button to start server
        this.startButton = new JButton("START");
        this.changeFont(this.startButton, 1);
        this.baseConstraints.gridx = 1;
        this.baseConstraints.gridy = 6;
        this.baseConstraints.gridwidth = 2;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 1.0;
        this.baseConstraints.weighty = 1.0;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(startButton, baseConstraints);

        //Button to stop server under start button
        this.stopButton = new JButton("STOP");
        this.changeFont(this.stopButton, 1);
        this.baseConstraints.gridx = 1;
        this.baseConstraints.gridy = 7;
        this.baseConstraints.gridwidth = 2;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 1.0;
        this.baseConstraints.weighty = 1.0;
        //this.baseConstraints.insets = null;
        this.baseConstraints.anchor = GridBagConstraints.CENTER;
        this.baseConstraints.fill = GridBagConstraints.BOTH;
        this.add(stopButton, baseConstraints);

        //Button to force quit on bottom
        this.quitButton = new JButton("QUIT");
        this.changeFont(this.quitButton, 1);
        this.baseConstraints.gridx = 0;
        this.baseConstraints.gridy = 8;
        this.baseConstraints.gridwidth = 4;
        this.baseConstraints.gridheight = 1;
        this.baseConstraints.ipadx = 0;
        this.baseConstraints.ipady = 0;
        this.baseConstraints.weightx = 1.0;
        this.baseConstraints.weighty = 1.0;
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
                Thread s = new Thread(new Runnable() {
                    public void run() {
                        launchServer();
                    }
                });
                Thread c = new Thread(new Runnable() {
                    public void run() {
                        launchClient();
                    }
                });
                s.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException iex) {

                }
                c.start();
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
    private void changeFont(JComponent base, int size) {
        Font origin, prime;

        origin = base.getFont();
        prime = new Font(origin.getFontName(), origin.getStyle(), origin.getSize() + size);

        base.setFont(prime);
        return;
    }
}
