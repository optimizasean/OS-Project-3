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
    private static Server server = null;

    //Client - store in arraylist or some shit????????
    private Client[] clients = null;

    //FINAL
    public static final int NUMBER_OF_CLIENTS = 5;

    public static void main(String[] args) {
        Main main = new Main();
        //main.start();
        main.GUI();
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
        clients = new Client[NUMBER_OF_CLIENTS];
        for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
            Client client = new Client(i);
            clients[i] = client;
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
    }
}
