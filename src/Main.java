//Operating Systems Project (osp) package
package osp;

//Special
import javax.swing.SwingUtilities;

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

/*****************************************************************\
 * Main class launches the application and holds many of the
 * constants utilized by our application.
 * 
 * @author Shadowbomb
\*****************************************************************/
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

    //Thread Stop
    public static volatile boolean stopThreads = false;

    /*****************************************************************\
     * Main {@link #main(String[]) main} launches the application and
     * starts most of the parts of our GUI. Uses the built in logging
     * function and the {@link #run() run} function to non-static many
     * fields as well as the
     * {@link osp.DirectoryStructure#makeBackgroundLog() DirectoryStructure background log}
     * function to begin logging the environment events early.
     * 
     * @param String[]: The arguments passed in by command line
     * @return void
     * @see {@link #log(String) log}
     * @see {@link #run() run}
     * @see {@link osp.DirectoryStructure#makeBackgroundLog() DirectoryStructure background log}
    \*****************************************************************/
    public static void main(String[] args) {
        //Make Background Log
        DirectoryStructure.makeBackgroundLog();
        Main.log("[Main] Main logger created");
        Main main = new Main();
        //main.start();
        main.run();
    }

    /*****************************************************************\
     * Main {@link #Main() Main} is the blank constructor we use to
     * instantiate the main class and therefore un-static many fields.
     * 
     * @return Main
    \*****************************************************************/
    public Main() {}

    /*****************************************************************\
     * Main {@link #start() start} method is the old method we used to
     * use to start the application but this is now deprecated. See the
     * newer {@link #run() run} method we use to launch everything now.
     * It calls the GUI function which loads all of the visuals.
     * 
     * @return void
     * @see {@link #GUI() GUI}
     * @see {@link #run() run}
     * @deprecated
    \*****************************************************************/
    public void start() {
        SwingUtilities.invokeLater (new Runnable() {
            public void run() {
                GUI();
            }
        });
    }

    /*****************************************************************\
     * Main {@link #run() run} method is the new method we use to launch
     * almost everything in the program.  It logs in the
     * {@link #log(String) log} function and utilizes the
     * {@link osp.DirectoryStructure DirectoryStructure} class and its
     * {@link osp.DirectoryStructure#ensureStructure() ensureStructure}
     * method as well as the {@link #GUI() GUI} method to launch all of
     * the visuals.
     * 
     * @return void
     * @see {@link #log(String) log}
     * @see {@link osp.DirectoryStructure DirectoryStructure}
     * @see {@link osp.DirectoryStructure#ensureStructure() ensureStructure}
     * @see {@link #GUI() GUI}
    \*****************************************************************/
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

    /*****************************************************************\
     * Main {@link #GUI() GUI} method is the new method we use to build
     * the visual base of the GUI for our entire Application. It uses
     * the {@link #log(String) log} function to record events.
     * 
     * @return void
     * @see {@link #log(String) log}
    \*****************************************************************/
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

    /*****************************************************************\
     * Main {@link #log() log} method is the new method we use to log
     * almost every single system event in our Application. It
     * accomplishes this using the {@link osp.GlobalLogger GlobalLogger}
     * class and its
     * {@link osp.GlobalLogger#writeBackground(String) writeBackground}
     * method to save to the file synchronously with a timestamp from
     * all components.
     * 
     * @return void
     * @exception IOException
     * @see {@link osp.GlobalLogger GlobalLogger}
     * @see {@link osp.GlobalLogger#writeBackground(String) writeBackground}
    \*****************************************************************/
    public static void log(String log) {
        try {
            GlobalLogger.writeBackground(log);
        } catch (IOException iex) {}
        return;
    }
}
