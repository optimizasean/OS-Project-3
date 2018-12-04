//Operating Systems Project (osp) package
package osp;

//Frames, Panes, and Panels
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComponent;

//Layouts and Constraints
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;

//Labels and Text
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

//Buttons
import javax.swing.JButton;

//Color
import java.awt.Color;

//Fonts
import java.awt.Font;

//Events
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Client
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Utilities
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


/***************************
 * Client class is used for the client
 * connections to the server and will
 * also provide the GUI interface to
 * interact with them whenever they
 * successfully connect.
 ***************************/
public class Client extends JPanel {
    /**************************
     * Define Swing variables
     **************************/
    private JPanel readPanel = null;
    private JPanel writePanel = null;
    private JPanel viewPanel = null;
    private JScrollPane pcLogPane = null;
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
    private JButton writeMinus = null;

    //Logger
    LocalLogger logger = null;

    //Client
    private int clientNumber = 0;
    private int port = 0;
    private static String status = "idle";
	private static Queue<VectorClock> memory = new LinkedList<VectorClock>();
	private static int counter = 0;//keep track of how many PCs responded
	private static final Semaphore lock = new Semaphore(1);//lock for writing critical section
    private Socket socket = null;

    //Client constructor to setup basic client
    public Client(int clientNumber) {
        this.clientNumber = clientNumber;
        this.GUI();
    }

    private void GUI() {
        this.GUIVisual();
        this.GUIFunctional();
        return;
    }

    public void launch() {
        try {
            this.port = Server.port;
            this.socket = new Socket("127.0.0.1", this.port);
            Scanner k = new Scanner(System.in);
            
            ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            
            VectorClock clock = (VectorClock) ois.readObject();//set clock associated with the PC
            
            
            Thread command = new Thread(new Runnable() {
				public void run() {
					try {
						
						System.out.println("PC"+clock.ID);
						while(true) {
							
							String command = k.nextLine();//user input (read, write)
							
							if(command.equals("read")) {
								status = "reading";
								clock.inc();
								GlobalLogger.write(clock, "request to read");
								oos.writeUTF("read_request");
								oos.writeObject(clock);
								oos.flush();
							}
							
							if(command.equals("write")) {
								status = "writing";
								clock.inc();
								GlobalLogger.write(clock, "request to write");
								oos.writeUTF("write_request");
								oos.writeObject(clock);
								oos.flush();
							}
							
							if(command.equals("quit")) {
								oos.writeObject("quit");
								oos.flush();
							}
						}
					}
					catch(IOException e) {
						e.printStackTrace();
					}
				}
			});
            
            Thread response = new Thread(new Runnable() {
				public void run() {
					
					try {
						while(true) {
							String msg = ois.readUTF();
							VectorClock temp = (VectorClock) ois.readObject();
							
							if(msg.equals("read_request")) {
								clock.inc();
								clock.merge(temp);
								GlobalLogger.write(clock, "received read request from PC"+temp.ID);
								
								clock.inc();
								GlobalLogger.write(clock, "replied OK to PC"+temp.ID+" read request");

								oos.writeUTF("read_reply");
                                oos.reset();
								oos.writeObject(clock);
								oos.writeInt(temp.ID);
								oos.flush();
							}//end read_request
							
							if(msg.equals("read_reply")) {
								clock.inc();
								clock.merge(temp);
								GlobalLogger.write(clock, "PC"+temp.ID+" replied OK to read request");
								
								//if PC1 responded OK, perform read action
								clock.inc();
								Read.makeCopy();
								GlobalLogger.write(clock, "reads file");
                                Thread.sleep(3000);//simulate time taken to read file
								Read.deleteCopy();
								status = "idle";
								System.out.println("done reading");
							}//end read_reply
							
							
							if(msg.equals("write_request")) {
								
								clock.inc();
								clock.merge(temp);
								GlobalLogger.write(clock, "received write request from PC"+temp.ID);
								
								//if this PC is idle respond OK
								if(status.equals("idle")) {
									clock.inc();
									GlobalLogger.write(clock, "replied OK to PC"+temp.ID+" write request");

									oos.writeUTF("write_reply");
                                    oos.reset();
									oos.writeObject(clock);
									oos.writeInt(temp.ID);
									oos.flush();
								}
								
								//if this PC is also reading, respond:
								if(status.equals("writing")) {
									String cmp = VectorClock.compare(clock, temp);
									
									//if this PC requested after, respond OK
									if(cmp.equals("second->first")) {
										clock.inc();
										GlobalLogger.write(clock, "replied OK to PC"+temp.ID+" write request");

										oos.writeUTF("write_reply");
                                        oos.reset();
										oos.writeObject(clock);
										oos.writeInt(temp.ID);
										oos.flush();
									}
									//if this PC requested earlier, finish critical section first
									else if(cmp.equals("first->second")) {
										{
											//Add to queue to be processed later
											memory.add(temp);
										}
									}
									else {
										System.out.println("write_request error");
									}
								}
							}//end write_request
							
							if(memory.peek() != null && !status.equals("writing")) {
								temp = memory.remove();
								
								clock.inc();
								GlobalLogger.write(clock, "replied OK to PC"+temp.ID+" write request");

								oos.writeUTF("write_reply");
                                oos.reset();
								oos.writeObject(clock);
								oos.writeInt(temp.ID);
								oos.flush();
							}
							
							if (msg.equals("write_reply")) {
									
								clock.inc();
								clock.merge(temp);
								GlobalLogger.write(clock, "PC"+temp.ID+" replied OK to write request");
								
								//to ensure multiple clients don't access this variable at same time
								lock.acquire();
								counter++;
								
								//if the other 4 PCs respond, perform write action
								if(counter > 3) {
									clock.inc();
									Write.downloadFile(clock);
									GlobalLogger.write(clock, "downloaded file from PC1");
									
									clock.inc();
									Write.writeFile(clock);
									GlobalLogger.write(clock, "writes to file");
                                    Thread.sleep(3000);//simulate time taken to write to file
									Write.returnFile(clock);
									
									counter = 0;//reset if we get another writing command
									status = "idle";
									System.out.println("done writing");
								}
								lock.release();
							}//end write_reply

						}//while loop
						
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
                    
            command.start();
            response.start();
        } catch (UnknownHostException uhex) {

        } catch (IOException iex) {

        } catch (Exception ex) {

        }

        return;
    }

    private void visualLog(String log) {
        this.pcLog.append(log);
        return;
    }

    private void GUIVisual() {
        //Set base panel background
        this.setBackground(Color.GRAY);

        //Set base panel layout
        base = new BorderLayout();
        this.setLayout(base);

        //Add base panel label north(top)
        windowLabel = new JLabel("PC " + this.clientNumber);
        this.add(windowLabel, BorderLayout.NORTH);

        //Prepare and add west(left) panel for +/- of setting read
        this.readPanel = new JPanel();
        this.readPanel.setBackground(Color.LIGHT_GRAY);
        this.readLayout = new BoxLayout(readPanel, BoxLayout.Y_AXIS);
        this.readPanel.setLayout(readLayout);
        this.readLabel = new JLabel("READ");
        this.readPanel.add(readLabel);
        this.readPlus = new JButton("+");
        this.readPanel.add(readPlus);
        this.readMinus = new JButton("-");
        this.readPanel.add(readMinus);
        this.add(readPanel, BorderLayout.WEST);

        //Prepare and add pclog to the center
        this.pcLog = new JTextArea(4, 30);
        this.pcLog.setEditable(false);
        this.pcLogPane = new JScrollPane(pcLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.pcLogPane.setBackground(Color.WHITE);
        this.add(pcLogPane, BorderLayout.CENTER);

        //Prepare and add the write panel for +/- to east(right)
        this.writePanel = new JPanel();
        this.writePanel.setBackground(Color.LIGHT_GRAY);
        this.writeLayout = new BoxLayout(writePanel, BoxLayout.Y_AXIS);
        this.writePanel.setLayout(writeLayout);
        this.writeLabel = new JLabel("WRITE");
        this.writePanel.add(writeLabel);
        this.writePlus = new JButton("+");
        this.writePanel.add(writePlus);
        this.writeMinus = new JButton("-");
        this.writePanel.add(writeMinus);
        this.add(writePanel, BorderLayout.EAST);

        //Prepare the text view panel for read and write percentages to go south(bottom)
        this.viewPanel = new JPanel();
        this.viewPanel.setBackground(Color.LIGHT_GRAY);
        this.viewLayout = new GridLayout(2, 2);
        this.viewPanel.setLayout(viewLayout);
        this.readViewLabel = new JLabel("Read: %");
        this.viewPanel.add(readViewLabel);
        this.readNumber = new JTextField("??");
        this.viewPanel.add(readNumber);
        this.writeViewLabel = new JLabel("Write: %");
        this.viewPanel.add(writeViewLabel);
        this.writeNumber = new JTextField("??");
        this.viewPanel.add(writeNumber);
        this.add(viewPanel, BorderLayout.SOUTH);

        return;
    }
    private void GUIFunctional() {
        this.readPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("READPLUS BUTTON PUSHED");
            }
        });

        this.readMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("READMINUS BUTTON PUSHED");
            }
        });

        this.writePlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WRITEPLUS BUTTON PUSHED");
            }
        });

        this.writeMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WRITEMINUS BUTTON PUSHED");
            }
        });

        return;
    }
    private void changeFont(JComponent base, int size) {
        Font origin, prime;

        origin = base.getFont();
        prime = new Font(origin.getFontName(), origin.getStyle(), origin.getSize()-1);

        base.setFont(prime);
        return;
    }
}
