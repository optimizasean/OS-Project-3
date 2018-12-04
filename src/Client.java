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

//Dimensions
import java.awt.Dimension;

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

    //Display Text
    private String plus = "+";
    private String minus = "-";

    //Logger
    private LocalLogger logger = null;

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
        Main.log("[Client] Creating Client: " + clientNumber);
        this.clientNumber = clientNumber;
        Main.log("[Client: " + this.clientNumber + "] Client Number Set: " + clientNumber);
        Main.log("[Client: " + this.clientNumber + "] Starting GUI");
        this.GUI();
        Main.log("[Client: " + this.clientNumber + "] Client Complete");
    }

    private void GUI() {
        Main.log("[Client: " + this.clientNumber + "] Creating Visual");
        this.GUIVisual();
        Main.log("[Client: " + this.clientNumber + "] Linking Functional");
        this.GUIFunctional();
        Main.log("[Client: " + this.clientNumber + "] GUI Complete");
        return;
    }

    public void launch() {
        Main.log("[Client: " + this.clientNumber + "] Launching Client");
        try {
            Main.log("[Client: " + this.clientNumber + "] Getting Port from Server");
            this.port = Server.port;
            Main.log("[Client: " + this.clientNumber + "] Getting Socket Connection");
            this.socket = new Socket("127.0.0.1", this.port);
            
            //DELETE THIS AFTER IMPPLEMENT RATIO
            Scanner k = new Scanner(System.in);
            
            Main.log("[Client: " + this.clientNumber + "] Starting Object Streams");
            ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            
            //Reading Vector Clock
            Main.log("[Client: " + this.clientNumber + "] Setting Vector Clock");
            VectorClock clock = (VectorClock) ois.readObject();//set clock associated with the PC
            
            Main.log("[Client: " + this.clientNumber + "] Creating Command Thread");
            Thread command = new Thread(new Runnable() {
				public void run() {
					try {
						Main.log("[Client: " + clientNumber + "] Command thread started successfully");
						System.out.println("PC"+clock.ID);
						while(true) {
							
							String command = k.nextLine();//user input (read, write)
							
							if(command.equals("read")) {
								status = "reading";
                                clock.inc();
                                
                                //LOG
                                log(clock, "request to read");

								oos.writeUTF("read_request");
								oos.writeObject(clock);
								oos.flush();
							}
							
							if(command.equals("write")) {
								status = "writing";
                                clock.inc();
                                
                                //LOG
                                log(clock, "request to write");

								oos.writeUTF("write_request");
								oos.writeObject(clock);
								oos.flush();
							}
							
							if(command.equals("quit")) {
								oos.writeObject("quit");
								oos.flush();
							}
                        }
                        //Main.log("[Client: " + clientNumber + "] Command Thread Ended");
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			});
            
            Main.log("[Client: " + this.clientNumber + "] Creating Response Thread");
            Thread response = new Thread(new Runnable() {
				public void run() {
					Main.log("[Client: " + clientNumber + "] Response thread started sucessfully");
					try {
						while(true) {
							String msg = ois.readUTF();
							VectorClock temp = (VectorClock) ois.readObject();
							
							if(msg.equals("read_request")) {
								clock.inc();
                                clock.merge(temp);
                                
                                //LOG
                                log(clock, "received read request from PC" + temp.ID);
								
                                clock.inc();
                                
                                //LOG
                                log(clock, "replied OK to PC" + temp.ID + " read request");

								oos.writeUTF("read_reply");
                                oos.reset();
								oos.writeObject(clock);
								oos.writeInt(temp.ID);
								oos.flush();
							}//end read_request
							
							if(msg.equals("read_reply")) {
								clock.inc();
                                clock.merge(temp);
                                
                                //LOG
                                log(clock, "PC" + temp.ID + " replied OK to read request");
                                
								//if PC1 responded OK, perform read action
								clock.inc();
                                Read.makeCopy();
                                
                                //LOG
                                log(clock, "reads file");

                                Thread.sleep(3000);//simulate time taken to read file
								Read.deleteCopy();
								status = "idle";
								System.out.println("done reading");
							}//end read_reply
							
							
							if(msg.equals("write_request")) {
								
								clock.inc();
                                clock.merge(temp);
                                
                                //LOG
                                log(clock, "received write request from PC" + temp.ID);
								
								//if this PC is idle respond OK
								if(status.equals("idle")) {
                                    clock.inc();
                                    
                                    //LOG
                                    log(clock, "replied OK to PC" + temp.ID + " write request");

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
                                        
                                        //LOG
										log(clock, "replied OK to PC" + temp.ID + " write request");

										oos.writeUTF("write_reply");
                                        oos.reset();
										oos.writeObject(clock);
										oos.writeInt(temp.ID);
										oos.flush();
									}
									//if this PC requested earlier, finish critical section first
									else if(cmp.equals("first->second")) {
											//Add to queue to be processed later
											memory.add(temp);
									}
									else {
										System.out.println("write_request error");
									}
								}
							}//end write_request
							
							if(memory.peek() != null && !status.equals("writing")) {
								temp = memory.remove();
								
                                clock.inc();
                                
                                //LOG
								log(clock, "replied OK to PC" + temp.ID + " write request");


								oos.writeUTF("write_reply");
                                oos.reset();
								oos.writeObject(clock);
								oos.writeInt(temp.ID);
								oos.flush();
							}
							
							if (msg.equals("write_reply")) {
									
								clock.inc();
                                clock.merge(temp);
                                
                                //LOG
                                log(clock, "PC" + temp.ID + " replied OK to write request");
								
								//to ensure multiple clients don't access this variable at same time
								lock.acquire();
								counter++;
								
								//if the other 4 PCs respond, perform write action
								if(counter > 3) {
									clock.inc();
                                    Write.downloadFile(clock);
                                    
                                    //LOG
                                    log(clock, "downloaded file from PC1");
									
									clock.inc();
                                    Write.writeFile(clock);
                                    
                                    //LOG
                                    log(clock, "writes to file");
                                    
                                    Thread.sleep(3000);//simulate time taken to write to file
									Write.returnFile(clock);
									
									counter = 0;//reset if we get another writing command
									status = "idle";
									System.out.println("done writing");
								}
								lock.release();
							}//end write_reply

						}//while loop
						//Main.log("[Client: " + clientNumber + "] Response Thread Ended");
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
            
            Main.log("[Client: " + this.clientNumber + "] Start Command Thread");
            command.start();
            Main.log("[Client: " + this.clientNumber + "] Command thread started");

            Main.log("[Client: " + this.clientNumber + "] Start Response thread");
            response.start();
            Main.log("[Client: " + this.clientNumber + "] Response Thread started");
        } catch (UnknownHostException uhex) {

        } catch (IOException iex) {

        } catch (Exception ex) {

        }
        Main.log("[Client: " + this.clientNumber + "] Client Launch Complete");
        return;
    }

    private void log(VectorClock clock, String log) {
        try {
            //PC clock based log
            GlobalLogger.writePC(clock, log);
            //Controller clock and timestamp based log
            GlobalLogger.writeController(clock, log);
        } catch (IOException iex) {}
        //Viual
        this.pcLog.append(clock + log);
        //PC timestamp based log
        this.logger.log(log);   
        //Controller Log
        Main.server.visualLog(clock + log);

        return;
    }

    private void GUIVisual() {
        Main.log("[Client: " + this.clientNumber + "] Preparing Visual of GUI");
        //Set base panel background
        this.setBackground(Color.GRAY);

        //Set base panel layout
        Main.log("[Client: " + this.clientNumber + "] Setting base Layout");
        this.base = new BorderLayout();
        this.setLayout(this.base);
        Main.log("[Client: " + this.clientNumber + "] base Layout Set");

        //Add base panel label north(top)
        Main.log("[Client: " + this.clientNumber + "] Creating Window label");
        this.windowLabel = new JLabel("PC " + this.clientNumber);
        this.changeFont(this.windowLabel, 5);
        this.add(this.windowLabel, BorderLayout.NORTH);
        Main.log("[Client: " + this.clientNumber + "] Window label created and added");

        //Prepare and add west(left) panel for +/- of setting read
        Main.log("[Client: " + this.clientNumber + "] Creating Read panel for changing read percentage");
        this.readPanel = new JPanel();
        this.readPanel.setBackground(Color.LIGHT_GRAY);
        this.readLayout = new BoxLayout(this.readPanel, BoxLayout.Y_AXIS);
        this.readPanel.setLayout(this.readLayout);
        Main.log("[Client: " + this.clientNumber + "] Setting read panel label");
        this.readLabel = new JLabel("READ");
        this.changeFont(this.readLabel, -1);
        this.readPanel.add(this.readLabel);
        Main.log("[Client: " + this.clientNumber + "] Adding read panel + button");
        this.readPlus = new JButton(this.plus);
        this.changeFont(this.readPlus, -5);
        this.readPanel.add(this.readPlus);
        Main.log("[Client: " + this.clientNumber + "] Adding read panel - button");
        this.readMinus = new JButton(this.minus);
        this.changeFont(this.readMinus, -5);
        this.readPanel.add(this.readMinus);
        this.add(this.readPanel, BorderLayout.WEST);
        Main.log("[Client: " + this.clientNumber + "] Read panel complete");

        //Prepare and add pclog to the center
        Main.log("[Client: " + this.clientNumber + "] Creating PC Log box");
        this.pcLog = new JTextArea(5, 30);
        this.changeFont(this.pcLog, -3);
        this.pcLog.setEditable(false);
        this.pcLogPane = new JScrollPane(this.pcLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.pcLogPane.setBackground(Color.WHITE);
        this.add(this.pcLogPane, BorderLayout.CENTER);
        Main.log("[Client: " + this.clientNumber + "] PC Log Box complete");

        //Prepare and add the write panel for +/- to east(right)
        Main.log("[Client: " + this.clientNumber + "] Creating Write Panel");
        this.writePanel = new JPanel();
        this.writePanel.setBackground(Color.LIGHT_GRAY);
        this.writeLayout = new BoxLayout(this.writePanel, BoxLayout.Y_AXIS);
        this.writePanel.setLayout(this.writeLayout);
        Main.log("[Client: " + this.clientNumber + "] Adding WRITE label to write panel");
        this.writeLabel = new JLabel("WRITE");
        this.changeFont(this.writeLabel, -1);
        this.writePanel.add(this.writeLabel);
        Main.log("[Client: " + this.clientNumber + "] Creating Write + button");
        this.writePlus = new JButton(this.plus);
        this.changeFont(this.writePlus, -5);
        this.writePanel.add(this.writePlus);
        Main.log("[Client: " + this.clientNumber + "] Creating Write - button");
        this.writeMinus = new JButton(this.minus);
        this.changeFont(this.writeMinus, -5);
        this.writePanel.add(this.writeMinus);
        this.add(this.writePanel, BorderLayout.EAST);
        Main.log("[Client: " + this.clientNumber + "] Complete Write panel");

        //Prepare the text view panel for read and write percentages to go south(bottom)
        Main.log("[Client: " + this.clientNumber + "] Preparing south percentage panel");
        this.viewPanel = new JPanel();
        this.viewPanel.setBackground(Color.LIGHT_GRAY);
        this.viewLayout = new GridLayout(2, 2);
        this.viewPanel.setLayout(this.viewLayout);
        this.readViewLabel = new JLabel("Read: %");
        this.changeFont(this.readViewLabel, -2);
        this.viewPanel.add(this.readViewLabel);
        this.readNumber = new JTextField("??");
        this.changeFont(this.readNumber, -2);
        this.readNumber.setEditable(false);
        this.viewPanel.add(this.readNumber);
        this.writeViewLabel = new JLabel("Write: %");
        this.changeFont(this.writeViewLabel, -2);
        this.viewPanel.add(this.writeViewLabel);
        this.writeNumber = new JTextField("??");
        this.changeFont(this.writeNumber, -2);
        this.writeNumber.setEditable(false);
        this.viewPanel.add(this.writeNumber);
        this.add(this.viewPanel, BorderLayout.SOUTH);
        Main.log("[Client: " + this.clientNumber + "] Percentage Panel Complete");

        Main.log("[Client: " + this.clientNumber + "] GUI Visual Complete");
        return;
    }

    private void GUIFunctional() {
        Main.log("[Client: " + this.clientNumber + "] Beginning GUI Functionality");
        Main.log("[Client: " + this.clientNumber + "] Adding read+ button function");
        this.readPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("READPLUS BUTTON PUSHED");
            }
        });
        Main.log("[Client: " + this.clientNumber + "] Read+ button function added");

        Main.log("[Client: " + this.clientNumber + "] Adding Read- button function");
        this.readMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("READMINUS BUTTON PUSHED");
            }
        });
        Main.log("[Client: " + this.clientNumber + "] Read- buttno function added");

        Main.log("[Client: " + this.clientNumber + "] Adding write+ button function");
        this.writePlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WRITEPLUS BUTTON PUSHED");
            }
        });
        Main.log("[Client: " + this.clientNumber + "] Write+ button function added");

        Main.log("[Client: " + this.clientNumber + "] Adding Write- button function");
        this.writeMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WRITEMINUS BUTTON PUSHED");
            }
        });
        Main.log("[Client: " + this.clientNumber + "] Write- button function added");

        Main.log("[Client: " + this.clientNumber + "] GUI functionality added - complete");
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
