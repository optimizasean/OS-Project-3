package osp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Client {
	
	private static String status = "idle";
	private static Queue<VectorClock> memory = new LinkedList<VectorClock>();
	private static int counter = 0;//keep track of how many PCs responded
	private static Semaphore counterLock = new Semaphore(1);//lock for the counter
	private static Semaphore fileLock = new Semaphore(1);//handle file critical section
	
	public static void main (String[] args) throws IOException, ClassNotFoundException {
		
		Socket s = new Socket("127.0.0.1", 9001);
		Scanner k = new Scanner (System.in);
		
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		DataInputStream dis = new DataInputStream(s.getInputStream());
		
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
							dos.writeUTF("read_request");
							oos.writeObject(clock);
						}
						
						if(command.equals("write")) {
							status = "writing";
							clock.inc();
							GlobalLogger.write(clock, "request to write");
							dos.writeUTF("write_request");
							oos.writeObject(clock);
						}
						
						if(command.equals("quit")) {
							dos.writeUTF("quit");
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
						String msg = dis.readUTF();
						
						if(msg.equals("read_request")) {
							VectorClock requester = (VectorClock) ois.readObject();
							
							clock.inc();
							clock.merge(requester);
							GlobalLogger.write(clock, "received read request from PC"+requester.ID);
							
							clock.inc();
							GlobalLogger.write(clock, "replied OK to PC"+requester.ID+" read request");
							dos.writeUTF("read_reply");
							oos.writeObject(clock);
							oos.writeObject(requester);
						}//end read_request
						
						if(msg.equals("read_reply")) {
							VectorClock replier = (VectorClock) ois.readObject();
							
							clock.inc();
							clock.merge(replier);
							GlobalLogger.write(clock, "PC"+replier.ID+" replied OK to read request");
							
							//if PC1 responded OK, perform read action
							clock.inc();
							Read.makeCopy();
							GlobalLogger.write(clock, "reads file");
							Read.deleteCopy();
							status = "idle";
							System.out.println("done reading");
						}//end read_reply
						
						
						if(msg.equals("write_request")) {
							VectorClock requester = (VectorClock) ois.readObject();
							
							clock.inc();
							clock.merge(requester);
							GlobalLogger.write(clock, "received write request from PC"+requester.ID);
							
							//if this PC is idle respond OK
							if(status.equals("idle")) {
								clock.inc();
								GlobalLogger.write(clock, "replied OK to PC"+requester.ID+" write request");
								dos.writeUTF("write_reply");
								oos.writeObject(clock);
								oos.writeObject(requester);
							}
							
							//if this PC is also reading, respond:
							if(status.equals("writing")) {
								String cmp = VectorClock.compare(clock, requester);
								
								//if this PC requested after, respond OK
								if(cmp.equals("second->first")) {
									clock.inc();
									GlobalLogger.write(clock, "replied OK to PC"+requester.ID+" write request");
									dos.writeUTF("write_reply");
									oos.writeObject(clock);
									oos.writeObject(requester);
								}
								//if this PC requested earlier, finish critical section first
								else if(cmp.equals("first->second")) {
									{
										//Add to queue to be processed later
										memory.add(requester);
									}
								}
								else {
									System.out.println("write_request error");
								}
							}
						}//end write_request
						
						if(memory.peek() != null && !status.equals("writing")) {
							VectorClock requester = memory.remove();
							
							clock.inc();
							GlobalLogger.write(clock, "replied OK to PC"+requester.ID+" write request");
							dos.writeUTF("write_reply");
							oos.writeObject(clock);
							oos.writeObject(requester);
						}
						
						if (msg.equals("write_reply")) {
							System.out.println("hey");
							counterLock.acquire();
							VectorClock replier = (VectorClock) ois.readObject();
							System.err.println("man");
							clock.inc();
							clock.merge(replier);
							GlobalLogger.write(clock, "PC"+replier.ID+" replied OK to write request");
							
							//to ensure multiple clients don't access this variable at same time
							
							counter++;
							counterLock.release();
							
							//if the other 4 PCs respond, perform write action
							if(counter > 3) {
								clock.inc();
								Write.downloadFile(clock);
								GlobalLogger.write(clock, "downloaded file from PC1");
								
								clock.inc();
								Write.writeFile(clock);
								GlobalLogger.write(clock, "writes to file");
								Write.returnFile(clock);
								
								counter = 0;//reset if we get another writing command
								status = "idle";
								System.out.println("done writing");
							}
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
		
	}
	
	
}
