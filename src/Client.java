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
import javax.swing.JScrollBar;

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
import javax.swing.SwingUtilities;
import java.util.Vector;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

//File
import java.io.File;


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
    private JScrollBar pcLogBar = null;
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
    private volatile String status = "idle";
	private Vector<VectorClock> queue = new Vector<VectorClock>();
	private int counter = 0;//keep track of how many PCs responded
	private volatile Semaphore fileLock = new Semaphore(1);
    private volatile Semaphore copyLock = new Semaphore(1);
    //private volatile Semaphore //streamLock = new Semaphore(1);
    private Semaphore commandLock = new Semaphore(1);
	private String command = null;
	private VectorClock clock = null;
	private volatile boolean done = true;
    private Socket socket = null;

    //PERCENTS
    private volatile int readPercentage = Constants.READ_DEFAULT;
    private volatile int writePercentage = Constants.WRITE_DEFAULT;

    //Threads
    public Thread instruction = null;
    public Thread task = null;

    //Client constructor to setup basic client
    public Client(int clientNumber) {
        //Set Client Number
        Main.log("[Client] Creating Client: " + clientNumber);
        this.clientNumber = clientNumber;
        Main.log("[Client: " + this.clientNumber + "] Client Number Set: " + clientNumber);

        //Set directory for Local Logger
        Main.log("[Client: " + this.clientNumber + "] Setting directory");
        String directory = null;
        switch (this.clientNumber) {
            //PC1
            case 1:
                directory = Constants.DIRECTORY_PATH_PC1;
                break;
            //PC2
            case 2:
                directory = Constants.DIRECTORY_PATH_PC2;
                break;
            //PC3
            case 3:
                directory = Constants.DIRECTORY_PATH_PC3;
                break;
            //PC 4
            case 4:
                directory = Constants.DIRECTORY_PATH_PC4;
                break;
            //PC 5
            case 5:
                directory = Constants.DIRECTORY_PATH_PC5;
                break;
            default:
                System.err.println("CLIENT:::::::FAILURE TO CREATE LOCAL LOGGER");
                System.exit(1);
        }
        Main.log("[Client: " + this.clientNumber + "] Directory Set");
        Main.log("[Client: " + this.clientNumber + "] Creating Local Logger");
        this.logger = new LocalLogger(new File(directory + Constants.FILE_LOCAL_LOG));
        Main.log("[Client: " + this.clientNumber + "] Local Logger Created");

        Main.log("[Client: " + this.clientNumber + "] Starting GUI");
        this.GUI();
        Main.log("[Client: " + this.clientNumber + "] GUI Started");

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
            visualLog("[Client: " + this.clientNumber + "] Getting port from Server");
            this.port = Main.server.port;
            Main.log("[Client: " + this.clientNumber + "] Getting Socket Connection");
            visualLog("[Client: " + this.clientNumber + "] Getting socket Connection");
            this.socket = new Socket("127.0.0.1", this.port);
            
            //DELETE THIS AFTER IMPPLEMENT RATIO
            //Scanner k = new Scanner(System.in);
            
            Main.log("[Client: " + this.clientNumber + "] Starting Object Streams");
            visualLog("[Client: " + this.clientNumber + "] Starting Object Streams");
            ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            
            //Reading Vector Clock
            Main.log("[Client: " + this.clientNumber + "] Setting Vector Clock");
            visualLog("[Client: " + this.clientNumber + "] Setting vector clock");
            this.clock = (VectorClock) ois.readObject();//set clock associated with the PC
            if (this.clock == null) System.err.println("ERROR CLOCK NULL");
            
            Main.log("[Client: " + this.clientNumber + "] Creating Instruction Thread");
            this.instruction = new Thread(new Runnable() {
				public void run() {
					try {
                        Main.log("[Client: " + clientNumber + "] Instruction thread started successfully");
                        visualLog("[Client: " + clientNumber + "] Instruction thread started successfully");

						while(true) {
                            //Check for stop and interrupt
							if (Main.stopThreads) Thread.currentThread().interrupt();
							//String command = k.nextLine();//user input (read, write)

                            commandLock.acquire();
                            while(!done);
                            Thread.sleep(2000);
                            command = Ratio.command(readPercentage, writePercentage);
							
							if(command.equals("read")) {
                                System.out.println("PC"+clock.ID+": request to read");
								status = "reading";
                                clock.inc();
                                
                                //LOG
                                clientLog(clock, "request to read");

                                //streamLock.acquire();
								oos.writeUTF("read_request");
							    oos.writeObject(clock);
							    oos.flush();
                                oos.reset();
                                //streamLock.release();
							}
							
							if(command.equals("write")) {
                                System.out.println("PC"+clock.ID+": request to write");
								status = "writing";
                                clock.inc();
                                clock.setWriteTime(clock);
                                
                                //System.out.println(clientNumber + " clock: " + clock.ID + " " + clock);
                                //LOG
                                clientLog(clock, "request to write");

                                //streamLock.acquire();
								oos.writeUTF("write_request");
							    oos.writeObject(clock);
							    oos.flush();
                                oos.reset();
                                //streamLock.release();
							}
							
							if(command.equals("quit")) {
                                //streamLock.acquire();
								oos.writeObject("quit");
                                oos.flush();
                                //streamLock.release();
							}
                        }
                        //Main.log("[Client: " + clientNumber + "] Instruction Thread Ended");
					} catch (InterruptedException iex) {
                        System.err.println("Interrupted!");
                    } catch(IOException ioex) {
						ioex.printStackTrace();
					}
				}
			});
            
            Main.log("[Client: " + this.clientNumber + "] Creating Task Thread");
            this.task = new Thread(new Runnable() {
				public void run() {
                    Main.log("[Client: " + clientNumber + "] Task thread started sucessfully");
                    visualLog("[Client: " + clientNumber + "] Task thread started successfully");
					try {
						while(true) {
                            //Check for stop and interrupt
							if (Main.stopThreads) Thread.currentThread().interrupt();

                            //streamLock.acquire();
							String msg = ois.readUTF();
							VectorClock temp = (VectorClock) ois.readObject();
                            //streamLock.release();
							
							if(msg.equals("read_request")) {
								clock.inc();
                                clock.merge(temp);
                                
                                //LOG
                                clientLog(clock, "received read request from PC" + temp.ID);
								
                                clock.inc();
                                
                                //LOG
                                clientLog(clock, "replied OK to PC" + temp.ID + " read request");

								//streamLock.acquire();
                                oos.writeUTF("read_reply");
                                oos.writeObject(clock);
                                oos.writeInt(temp.ID);
                                oos.flush();
                                oos.reset();
                                //streamLock.release();
							}//end read_request
							
							if(msg.equals("read_reply")) {
								clock.inc();
                                clock.merge(temp);
                                
                                //LOG
                                clientLog(clock, "PC" + temp.ID + " replied OK to read request");
                                
                                copyLock.acquire();
								//if PC1 responded OK, perform read action
								clock.inc();
                                Read.makeCopy();
                                
                                //LOG
                                clientLog(clock, "reads file");

                                Thread.sleep(2000);//simulate time taken to read file
								Read.deleteCopy();
								status = "idle";
								System.out.println("PC"+clock.ID+": done reading");
                                copyLock.release();
                                commandLock.release();
							}//end read_reply
							
							
							if(msg.equals("write_request")) {
								
								clock.inc();
                                clock.merge(temp);
                                
                                //LOG
                                clientLog(clock, "received write request from PC" + temp.ID);
								
								//if this PC is not writing, respond OK
								if(!status.equals("writing")) {
                                    clock.inc();
                                    
                                    //LOG
                                    clientLog(clock, "replied OK to PC" + temp.ID + " write request");

									//streamLock.acquire();
                                    oos.writeUTF("write_reply");
                                    oos.writeObject(clock);
                                    oos.writeInt(temp.ID);
                                    oos.flush();
                                    oos.reset();
                                    //streamLock.release();
								}
								
								//if this PC is writing, determine who requested first
								if(status.equals("writing")) {
									String cmp = VectorClock.compare(clock, temp);
									
									//if this PC requested after, respond OK
									if(cmp.equals("second->first") || (cmp.equals("first<->second") && clock.ID>temp.ID)) {
                                        clock.inc();
                                        
                                        //LOG
										clientLog(clock, "replied OK to PC" + temp.ID + " write request");

										//streamLock.acquire();
                                        oos.writeUTF("write_reply");
                                        oos.writeObject(clock);
                                        oos.writeInt(temp.ID);
                                        oos.flush();
                                        oos.reset();
                                        //streamLock.release();
									}
									//if this PC requested earlier, finish critical section first
									else if(cmp.equals("first->second") || (cmp.equals("first<->second") && clock.ID<temp.ID)) {
											//Add to queue to be processed later
											queue.add(temp);
                                            done = false;
                                            System.err.println("PC"+clock.ID+": added PC"+temp.ID+" to memory");
									}
									else {
										System.err.println("write_request error");
                                        System.err.println("PC"+clock.ID+": "+clock+temp);
									}
								}
							}//end write_request
							
							
							if (msg.equals("write_reply")) {
									
								clock.inc();
                                clock.merge(temp);
                                
                                //LOG
                                clientLog(clock, "PC" + temp.ID + " replied OK to write request");
								
                                fileLock.acquire();
								counter++;
								
								//if the other 4 PCs respond, perform write action
								if(counter > 3) {
									clock.inc();
                                    Write.downloadFile(clock);
                                    
                                    //LOG
                                    clientLog(clock, "downloaded file from PC1");
									
									clock.inc();
                                    Write.writeFile(clock);
                                    
                                    //LOG
                                    clientLog(clock, "writes to file");
                                    
                                    Thread.sleep(2000);//simulate time taken to write to file
									Write.returnFile(clock);
									
									counter = 0;//reset if we get another writing command
									status = "idle";
									System.out.println("PC"+clock.ID+": done writing");
                                    commandLock.release();
								}
                                fileLock.release();
							}//end write_reply


                            //Handles all requests in queue before proceeding
                            while(done == false && status.equals("idle")) {

                                System.err.println("PC"+clock.ID+": queue size is "+queue.size());
                                
                                temp = queue.remove(0);
                                System.err.println("PC"+clock.ID+": execute PC"+temp.ID);
                                
                                clock.inc();
                                clientLog(clock, "replied OK to PC"+temp.ID+" write request");
                                
                                //streamLock.acquire();
                                oos.writeUTF("write_reply");
                                oos.writeObject(clock);
                                oos.writeInt(temp.ID);
                                oos.flush();
                                oos.reset();
                                //streamLock.release();
                                
                                if(queue.size() <= 0){
                                    done = true;
                                }
						    }

						}//while loop
						//Main.log("[Client: " + clientNumber + "] Task Thread Ended");
					} catch (InterruptedException iex) {
                        System.err.println("Interrupted!");
                    } catch (ClassNotFoundException cnfex) {
                        System.err.println("Read Object Error: CNFEX");
                    } catch (IOException ioex) {
                        System.err.println("IO?");
                    }
				}
			});
            
            Main.log("[Client: " + this.clientNumber + "] Start Instruction Thread");
            this.instruction.start();
            Main.log("[Client: " + this.clientNumber + "] Instruction thread started");

            Main.log("[Client: " + this.clientNumber + "] Start Task thread");
            this.task.start();
            Main.log("[Client: " + this.clientNumber + "] Task Thread started");
        } catch (UnknownHostException uhex) {
            System.err.println("Why?");
        } catch (ClassNotFoundException cnfex) {
            System.err.println("CHRIS!");
        } catch (IOException ioex) {
            System.err.println("How?");
        }
        Main.log("[Client: " + this.clientNumber + "] Client Launch Complete?");
        return;
    }

    private void readPercentagePlus() {
        //Change
        if (this.readPercentage >= 100) {
            this.readPercentage = 0;
            this.writePercentage = 100;
        } else {
            this.readPercentage++;
            this.writePercentage--;
        }

        //View
        this.readNumber.setText(String.valueOf(this.readPercentage));
        this.writeNumber.setText(String.valueOf(this.writePercentage));

        return;
    }
    private void readPercentageMinus() {
        if (this.readPercentage <= 0) {
            this.readPercentage = 100;
            this.writePercentage = 0;
        } else {
            this.readPercentage--;
            this.writePercentage++;
        }
        
        //View
        this.readNumber.setText(String.valueOf(this.readPercentage));
        this.writeNumber.setText(String.valueOf(this.writePercentage));

        return;
    }
    private void writePercentagePlus() {
        if (this.writePercentage >= 100) {
            this.writePercentage = 0;
            this.readPercentage = 100;
        } else {
            this.readPercentage--;
            this.writePercentage++;
        }
        
        //View
        this.readNumber.setText(String.valueOf(this.readPercentage));
        this.writeNumber.setText(String.valueOf(this.writePercentage));

        return;
    }
    private void writePercentageMinus() {
        if (this.writePercentage <= 0) {
            this.writePercentage = 100;
            this.readPercentage = 0;
        } else {
            this.readPercentage++;
            this.writePercentage--;
        }
        
        //View
        this.readNumber.setText(String.valueOf(this.readPercentage));
        this.writeNumber.setText(String.valueOf(this.writePercentage));

        return;
    }

    private void clientLog(VectorClock clock, String log) {
        //System.out.println("CLIENTLOG: " + clock + " : " + log);
        try {
            //PC clock based log
            GlobalLogger.writePC(clock, log);
            //Controller clock and timestamp based log
            GlobalLogger.writeController(clock, log);
        } catch (IOException iex) {}
        //Visual
        visualLog(clock + log);
        //PC timestamp based log
        this.logger.log(log);   
        //Controller Log
        Main.server.visualLog(clock + log);

        return;
    }
    public void visualLog(String log) {
        this.pcLog.append(log + "\n");
        pcLogBar.setValue(pcLogBar.getMaximum() - 1);

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
        this.pcLog.setText("[Client] CLIENT VISUAL\n");
        this.pcLogPane = new JScrollPane(this.pcLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.pcLogPane.setBackground(Color.WHITE);
        this.add(this.pcLogPane, BorderLayout.CENTER);
        this.pcLogBar = this.pcLogPane.getVerticalScrollBar();
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
        this.readNumber = new JTextField(String.valueOf(this.readPercentage));
        this.changeFont(this.readNumber, -2);
        this.readNumber.setEditable(false);
        this.viewPanel.add(this.readNumber);
        this.writeViewLabel = new JLabel("Write: %");
        this.changeFont(this.writeViewLabel, -2);
        this.viewPanel.add(this.writeViewLabel);
        this.writeNumber = new JTextField(String.valueOf(this.writePercentage));
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
                visualLog("[CLIENT: " + clientNumber + "] READPLUS PUSHED");
                readPercentagePlus();
            }
        });
        Main.log("[Client: " + this.clientNumber + "] Read+ button function added");

        Main.log("[Client: " + this.clientNumber + "] Adding Read- button function");
        this.readMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("READMINUS BUTTON PUSHED");
                visualLog("[CLIENT: " + clientNumber + "] READMINUS PUSHED");
                readPercentageMinus();
            }
        });
        Main.log("[Client: " + this.clientNumber + "] Read- button function added");

        Main.log("[Client: " + this.clientNumber + "] Adding write+ button function");
        this.writePlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WRITEPLUS BUTTON PUSHED");
                visualLog("[CLIENT: " + clientNumber + "] WRITEPLUS PUSHED");
                writePercentagePlus();
            }
        });
        Main.log("[Client: " + this.clientNumber + "] Write+ button function added");

        Main.log("[Client: " + this.clientNumber + "] Adding Write- button function");
        this.writeMinus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("WRITEMINUS BUTTON PUSHED");
                visualLog("[CLIENT: " + clientNumber + "] WRITEMINUS PUSHED");
                writePercentageMinus();
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
