//Operating Systems Project (osp) package
package osp;

//Special
import javax.swing.SwingUtilities;

import jdk.nashorn.internal.objects.Global;
import osp.GlobalLogger;

//Frames, Panes, and Panels
import javax.swing.JFrame;
import javax.swing.JPanel;

//Layouts and Constraints
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

//Color
import java.awt.Color;

//Utilities
import java.util.Vector;

//CREATE MAIN LOGGER
import java.io.File;
import java.io.IOException;


public class Main {
    /**************************
     * Define Swing variables
     **************************/
    private JFrame frame = null;
    private JPanel mainPanel = null;
    private JPanel clientsPanel = null;
    private FlowLayout mainLayout = null;
    private BoxLayout clientsLayout = null;

    //Server
    public static Server server = null;

    //ServerThread
    public static Vector<ServerThread> stv = null;

    //Client
    public static Vector<Client> cv = null;

    public static void main(String[] args) {
        //Make Background Log
        try {
            //Background Log
            File file = new File(Constants.BACKGROUND_LOG_PATH);
            file.mkdirs();
            file = new File(Constants.BACKGROUND_LOG_PATH + Constants.FILE_BACKGROUND_LOG);
            file.createNewFile();
        } catch (IOException iex) {}
        Main.log("[Main] Main logger created");
        Main main = new Main();
        //main.start();
        main.run();
    }

    //Blank constructor
    public Main() {}

    //Start the GUI and everything else as a thread
    /*public void start() {
        SwingUtilities.invokeLater (new Runnable() {
            public void run() {
                GUI();
            }
        });
    }*/

    private void run() {
        //Ensuring Directory Structure
        Main.log("[Main] Ensuring Directory Structure");
        DirectoryStructure ds = new DirectoryStructure();
        ds.ensureStructure();
        ds = null;

        //Starting GUI
        Main.log("[Main] Starting GUI");
        this.GUI();

        return;
    }


    private void GUI() {
        //Building GUI
        Main.log("[Main] Building GUI");
        Main.log("[Main] Creating Frame");
        frame = new JFrame("OSP3");
        Main.log("[Main] Beginning to build Main panel");
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainLayout = new FlowLayout();
        mainPanel.setLayout(mainLayout);
        Main.log("[Main] Main panel created");
        
        //Creating Clients panel and clients
        Main.log("[Main] Creating Clients Panel and clients");
        clientsPanel = new JPanel();
        clientsPanel.setBackground(Color.DARK_GRAY);
        clientsLayout = new BoxLayout(clientsPanel, BoxLayout.Y_AXIS);
        clientsPanel.setLayout(clientsLayout);
        Main.log("[Main] Creating Client Vector");
        Main.cv = new Vector<Client>();
        for (int i = 0; i < Constants.NUMBER_OF_CLIENTS; i++) {
            //Creating client number i
            Main.log("[Main] Created client: " + i);
            Client client = new Client(i + 1);
            cv.add(client);
            clientsPanel.add(client);
        }
        mainPanel.add(clientsPanel);
        Main.log("[Main] Clients panel complete");

        //Creating Server panel
        Main.log("[Main] Creating Server panel");
        server = new Server();
        mainPanel.add(server);
        Main.log("[Main] Server panel complete");

        //Building frame
        Main.log("[Main] Assembling frame");
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //Display
        Main.log("[Main] Packing Frame");
        frame.pack();
        Main.log("[Main] Displaying Frame");
        frame.setVisible(true);

        //Prepare ServerThread Vector
        Main.log("[Main] Preparing ServerThread Vector");
        Main.stv = new Vector<ServerThread>();
        Main.log("[Main] ServerThread Vector Created");

        Main.log("[Main] Main Complete");
        return;
    }

    public static void log(String log) {
        try {
            GlobalLogger.writeBackground(log);
        } catch (IOException iex) {}
        return;
    }
}
