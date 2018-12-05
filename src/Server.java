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

//Buttons
import javax.swing.JButton;
import javax.swing.JScrollBar;

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
    private JScrollBar serverLogBar = null;
    private JButton startButton = null;
    private JButton stopButton = null;
    private JButton quitButton = null;

    //Logger
    LocalLogger logger = null;
    File localLog = null;

    //Server
    private ServerSocket server = null;
	private Socket client = null;
    public int port = 0;
    private int id = 1;

    public Server() {
        Main.log("[Server] Starting Logger");
        this.startLogger();
        Main.log("[Server] Starting GUI");
        this.GUI();
    }

    private void startLogger() {
        Main.log("[Server] Starting Loggers");
        this.localLog = new File(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_LOCAL_LOG);
        this.logger = new LocalLogger(this.localLog);
    }
    
    /* OUTDATED DO NOT USE!!!!!!!!!!!!!
    public void log(String log) {
        try {
            GlobalLogger.writeController(null, message);
        } catch (IOException iex) {}
        this.visualLog(log);
        this.logger.log(log);
        return;
    }*/

    public void visualLog(String log) {
        this.serverLog.append(log + "\n");
        serverLogBar.setValue(serverLogBar.getMaximum() - 1);

        return;
    }

    private void launchServer() {
        Main.log("[Server] Server Launching");
        try {
            Main.log("[Server] Getting new port");
            this.refreshPort();
            Main.log("[Server] Setting new port");
            this.server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for client");
            Main.log("[Server] Server started, waiting for clients");
            
            this.id = 1;
            Main.log("[Server] Begin listening for requests");
            while(this.id < 6) {
                this.client = null;
                try {
                    Main.log("[Server] Listening for connection request");
                    visualLog("[Server] Listening for connection request");
                    this.client = this.server.accept();
                    System.out.println("PC" + this.id + " Accepted");
                    Main.log("[Server] Accepted PC: " + this.id);
                    visualLog("[Server] Accepted PC: " + this.id);
                    
                    Main.log("[Server] Initializing Streams: " + this.id);
                    visualLog("[Server] Initializing Streams: " + this.id);
                    ObjectOutputStream oos = new ObjectOutputStream(this.client.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(this.client.getInputStream());
                    Main.log("[Server] Streams Initialized:" + this.id);
                    visualLog("[Server] Streams Initialized:" + this.id);
                    
                    Main.log("[Server] Creating ServerThread: " + this.id);
                    visualLog("[Server] Creating ServerThread: " + this.id);
                    ServerThread st = new ServerThread(this, this.client, this.id, oos, ois);
                    Main.log("[Server] Adding ServerThread to vector: " + this.id);
                    visualLog("[Server] Adding ServerThread to vector: " + this.id);
                    Main.stv.add(st);
                    Main.log("[Server] Creating Thread: ServerThread: " + this.id);
                    visualLog("[Server] Creating Thread: ServerThread: " + this.id);
                    Thread t = new Thread(st);
                    Main.log("[Server] Starting Thread ServerThread: " + this.id);
                    visualLog("[Server] Starting Thread ServerThread: " + this.id);
                    t.start();
                    Main.log("[Server] Started Thread: ServerThread: " + this.id);
                    visualLog("[Server] Started Thread: ServerThread: " + this.id);
                    this.id++;
                    Main.log("[Server] Readying for new ID: " + this.id);
                    visualLog("[Server] Readying for new ID: " + this.id);
                } catch (IOException iex) {
                    Main.log("[Server] Error: ServerThread: " + this.id + "ERROR????????");
                    visualLog("[Server] Error: ServerThread: " + this.id + "ERROR????????");
                    System.err.println("ERROR????????");
                }
            }
        } catch(IOException iex) {
            Main.log("[Server] Error: ServerThread: " + this.id + "ERRORRRRRRRRRRRR");
            System.err.println("ERRORRRRRRRRRRRR");
        }
        return;
    }

    private void launchClient() {
        Main.log("[Server] Launching Clients");
        Client client = null;
        for (int i = 0; i < Constants.NUMBER_OF_CLIENTS; i++) {
            client = Main.cv.get(i);
            Main.log("[Server] Launching Client: " + (i + 1));
            client.launch();
            Main.log("[Server] Client Launched: " + (i + 1));
        }
        Main.log("[Server] Clients Launched");
        return;
    }

    public void refreshPort() {
        Main.log("[Server] Refreshing Port");
        port = Integer.parseInt(this.portField.getText());
        if (port < 0 || port > 65535) {
            Main.log("[Server] Invalid port, defaulting to: " + Constants.PORT_DEFAULT);
            port = Constants.PORT_DEFAULT;
            this.portField.setText("9001");
        }
        Main.log("[Server] Port Refresh Successful");
        return;
    }

    private void GUI() {
        Main.log("[Server] Preparing Visual of GUI");
        this.GUIVisual();
        Main.log("[Server] Connecting Functional of GUI");
        this.GUIFunctional();
        Main.log("[Server] GUI Complete");
        return;
    }
    private void GUIVisual() {
        Main.log("[Server] Doing GUI Visual");
        //Set base panel background
        this.setBackground(Color.GRAY);

        //Set base panel layout
        base = new GridBagLayout();
        this.setLayout(base);

        //Prepare constraints object for GridBagLayout
        this.baseConstraints = new GridBagConstraints();

        //Label for top of base panel
        Main.log("[Server] Creating Window Label for base panel");
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
        Main.log("[Server] Base Panel Label added Successfully");

        //Log for server on center of panel
        Main.log("[Server] Creating server log box");
        this.serverLog = new JTextArea(38, 40);
        this.changeFont(this.serverLog, -3);
        this.serverLog.setEditable(false);
        this.serverLog.setText("[Server] SERVER VISUAL\n");
        this.serverLogPane = new JScrollPane(serverLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.serverLogPane.setBackground(Color.WHITE);
        this.serverLogBar = this.serverLogPane.getVerticalScrollBar();
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
        Main.log("[Server] Server log box successfully added");

        //Label for port under server log
        Main.log("[Server] Creating Port Label");
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
        Main.log("[Server] Port label created successfully");

        //Field for taking port abot start button
        Main.log("[Server] Creating port button");
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
        Main.log("[Server] Port Button Created");

        //Button to start server
        Main.log("[Server] Creating Start button");
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
        Main.log("[Server] Start Button Created");

        //Button to stop server under start button
        Main.log("[Server] Creating Stop Button");
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
        Main.log("[Server] Stop Button Created");

        //Button to force quit on bottom
        Main.log("[Server] Creating QUIT button");
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
        Main.log("[Server] Quit button Added");

        Main.log("[Server] GUI Visual completed for Server panel");
        return;
    }
    private void GUIFunctional() {
        Main.log("[Server] Beginning GUI Function");
        Main.log("[Server] Adding Start Button Fucntionality");
        this.startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("START BUTTON PUSHED");
                Main.log("[Server] Start Button Pushed");
                visualLog("[Server] START BUTTON PUSHED");
                System.out.println("Start button pushed");
                Main.stopThreads = false;
                Main.log("[Server] Creating Server Launch Thread");
                Thread s = new Thread(new Runnable() {
                    public void run() {
                        Main.log("[Server] START BUTTON PUSHED - Launching Server");
                        launchServer();
                        Main.log("[Server] START BUTTON - Server Launched");
                    }
                });
                Main.log("[Server] Creating Client Launch Thread");
                Thread c = new Thread(new Runnable() {
                    public void run() {
                        Main.log("[Server] START BUTTON PUSHED - Launching Clients");
                        launchClient();
                        Main.log("[Server] START BUTTON PUSHED - Clients Launched");
                    }
                });
                Main.log("[Server] Starting Server Launch Thread");
                s.start();
                Main.log("[Server] Server Launch Thread Started");
                try {
                    Main.log("[Server] Wait");
                    Thread.sleep(1000);
                    Main.log("[Server] Wait over");
                } catch (InterruptedException iex) {

                }
                Main.log("[Server] Start Client Launch Thread");
                c.start();
                Main.log("[Server] Client Launch Thread Started");
            }
        });
        Main.log("[Server] Start button functional");

        Main.log("[Server] Adding stop button Functionality");
        this.stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.log("[Server] Stop Button Pushed");
                visualLog("[Server] STOP BUTTON PUSHED\n");
                System.out.println("STOP BUTTON PUSHED");
                //Interrupt Clients
                Main.log("[Server] Ending Clients");
                Main.stopThreads = true;
                for (int i = 0; i < Main.cv.size(); i++) {
                    Main.log("[Server] Get PC: " + i);
                    Client client = Main.cv.get(i);
                    try {
                        Main.log("[Server] Interrupting client instruction PC: " + i);
                        client.instruction.interrupt();
                        Main.log("[Server] Joining client instruction PC: " + i);
                        client.instruction.join();
                    } catch (InterruptedException iex) {}
                    try {
                        Main.log("[Server] Interrupting client task PC: " + i);
                        client.task.interrupt();
                        Main.log("[Server] Joining client task PC: " + i);
                        client.task.join();
                        Main.log("[Server] Stopped client PC: " + i);
                    } catch (InterruptedException iex) {}
                }
                Main.log("[Server] All Clients stopped?");
                //Interrupt ServerThreads
                Main.log("[Server] Ending Server Threads");
                for (int i = 0; i < Main.stv.size(); i++) {
                    Main.log("[Server] Get ServerThread: " + i);
                    ServerThread serverThread = Main.stv.get(i);
                    try {
                        Main.log("[Server] Interrupting server thread PC: " + i);
                        serverThread.interrupt();
                        Main.log("[Server] Joining server thread PC: " + i);
                        serverThread.join();
                        Main.log("[Server] Stopped serverthread PC: " + i);
                    } catch (InterruptedException iex) {}
                }
                //Interrupt Client c and Server s
                //No need because they are already stopped
                /*s.interrupt();
                s.join();
                c.interrupt();
                c.join();*/
                Main.stopThreads = false;
                Main.log("[Server] Stopped all ServerThreads?");
            }
        });
        Main.log("[Server] Stop button functional");

        Main.log("[Server] Adding quit button functionality");
        this.quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.log("[Server] Quit Button Pushed");
                visualLog("[Server] QUIT BUTTON PUSHED\n");
                System.out.println("QUIT BUTTON PUSHED");
                Main.log("[Server] Ending Program");
                System.exit(0);
            }
        });
        Main.log("[Server] Quit button functional");

        Main.log("[Server] Server panel completely functional");
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
