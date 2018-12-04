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
        DirectoryStructure ds = new DirectoryStructure();
        ds.ensureStructure();
        ds = null;
        this.GUI();
        return;
    }


    private void GUI() {
        frame = new JFrame("OSP3");
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainLayout = new FlowLayout();
        mainPanel.setLayout(mainLayout);
        
        //Creating Clients panel and clients
        clientsPanel = new JPanel();
        clientsPanel.setBackground(Color.DARK_GRAY);
        clientsLayout = new BoxLayout(clientsPanel, BoxLayout.Y_AXIS);
        clientsPanel.setLayout(clientsLayout);
        Main.cv = new Vector<Client>();
        for (int i = 0; i < Constants.NUMBER_OF_CLIENTS; i++) {
            Client client = new Client(i + 1);
            cv.add(client);
            clientsPanel.add(client);
        }
        mainPanel.add(clientsPanel);

        //Creating Server panel
        server = new Server();
        mainPanel.add(server);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);

        Main.stv = new Vector<ServerThread>();
    }
}
