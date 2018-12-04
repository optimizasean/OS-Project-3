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

//Exceptions
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
            file = new File(Constants.BACKGROUND_LOG_PATH);
            file.mkdirs();
            file = new File(Constants.BACKGROUND_LOG_PATH + Constants.FILE_BACKGROUND_LOG);
            file.createNewFile();
        } catch (IOException iex) {}

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
        log("Ensuring Directory Structure");
        DirectoryStructure ds = new DirectoryStructure();
        ds.ensureStructure();
        ds = null;

        //Starting GUI
        log("Starting GUI");
        this.GUI();

        return;
    }


    private void GUI() {
        //Building GUI
        log("Building GUI");
        log("Creating Frame");
        frame = new JFrame("OSP3");
        log("Beginning to build Main panel");
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainLayout = new FlowLayout();
        mainPanel.setLayout(mainLayout);
        log("Main panel created");
        
        //Creating Clients panel and clients
        log("Creating Clients Panel and clients");
        clientsPanel = new JPanel();
        clientsPanel.setBackground(Color.DARK_GRAY);
        clientsLayout = new BoxLayout(clientsPanel, BoxLayout.Y_AXIS);
        clientsPanel.setLayout(clientsLayout);
        log("Creating Client Vector");
        Main.cv = new Vector<Client>();
        for (int i = 0; i < Constants.NUMBER_OF_CLIENTS; i++) {
            //Creating client number i
            log("Created client: " + i);
            Client client = new Client(i + 1);
            cv.add(client);
            clientsPanel.add(client);
        }
        mainPanel.add(clientsPanel);
        log("Clients panel complete");

        //Creating Server panel
        log("Creating Server panel");
        server = new Server();
        mainPanel.add(server);
        log("Server panel complete");

        //Building frame
        log("Assembling frame");
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //Display
        log("Packing Frame");
        frame.pack();
        log("Displaying Frame");
        frame.setVisible(true);

        //Prepare ServerThread Vector
        log("Preparing ServerThread Vector");
        Main.stv = new Vector<ServerThread>();
    }

    public static void log(String log) {
        try {
            GlobalLogger.writeBackground(log);
        } catch (IOException iex) {}
    }
}
