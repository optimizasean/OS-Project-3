//Operating Systems Project (osp) package
package osp;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Semaphore;

public class ServerThread extends Thread {
    private Socket socket = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private VectorClock clock = null;
    private int id = 0;

    //Server link
    private Server server = null;

	//Semaphore
	private static final Semaphore lock = new Semaphore(1);
    
    ServerThread(Server server, Socket socket, int id, ObjectOutputStream oos, ObjectInputStream ois) {
		Main.log("[ServerThread] Creating ServerThread: " + id); 
        this.server = server;
        this.socket = socket;
        this.id = id;
        this.oos = oos;
        this.ois = ois;
		this.clock = new VectorClock(this.id);
		Main.log("[ServerThread: " + this.id + "] Created ServerThread");
    }
    
    public void run() {
		Main.log("[ServerThread: " + this.id + "] Running");
		//serverThreadLog("[ServerThread: " + this.id + "] Running");
		try {
			//this.log(clock + "Writing clock");
			oos.writeObject(this.clock);
			String msg;
			
			while((msg = ois.readUTF()) != null) {
				//Check for stop and interrupt
				if (Main.stopThreads) Thread.currentThread().interrupt();
				lock.acquire();
				//Check for stop and interrupt
				if (Main.stopThreads) Thread.currentThread().interrupt();
				/////////////////////////////READ/////////////////////////////
				
				if(msg.equals("read_request")) {
					//send read request to PC1
					this.clock = (VectorClock) ois.readObject();
					for(ServerThread st : Main.stv) {
						if(st.clock.ID == 1) {
							st.oos.writeUTF("read_request");
							st.oos.writeObject(this.clock);
							st.oos.flush();
							break;
						}
					}
				}
				if(msg.equals("read_reply")) {
					
					//send read reply to the PC that requested it
					this.clock = (VectorClock) ois.readObject();
					int target = ois.readInt();
					for(ServerThread st : Main.stv) {
						if(st.clock.ID == target) {
							st.oos.writeUTF("read_reply");
							st.oos.writeObject(this.clock);
							st.oos.flush();
							break;
						}
					}
				}
				//////////////////////////////////////////////////////////////
				
				
				/////////////////////////////WRITE////////////////////////////
				
				if(msg.equals("write_request")) {
					//send write request to all other PCs
					this.clock = (VectorClock) ois.readObject();
					for(ServerThread st : Main.stv) {
						if(st.clock.ID != this.clock.ID) {
							st.oos.writeUTF("write_request");
							st.oos.writeObject(this.clock);
							st.oos.flush();
						}
					}
				}
				
				if(msg.equals("write_reply")) {
					//send read reply to the PC that requested it
					this.clock = (VectorClock) ois.readObject();
					int target = ois.readInt();
					for(ServerThread st : Main.stv) {
						if(st.clock.ID == target) {
							st.oos.writeUTF("write_reply");
							st.oos.writeObject(this.clock);
							break;
						}
					}
				}
				
				//////////////////////////////////////////////////////////////	
				
				if(msg.equals("quit")) {
					this.socket.close();
					break;
				}
				
				lock.release();
				
			}//end while
			
			oos.close();
			ois.close();
			
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (ClassNotFoundException cnfex) {
			cnfex.printStackTrace();
		} catch (InterruptedException iex) {
			iex.printStackTrace();
		}
		Main.log("[ServerThread: " + this.id + "] Run Complete");
	}
    
    public void end() {
		Main.log("[ServerThread: " + this.id + "] Ending");
        try {
            this.socket.close();
        } catch (IOException iex) {
            System.err.println("Failure to end server");
        }
		Main.log("[ServerThread: " + this.id + "] Ended");
		
		return;
	}
	
	private void serverThreadLog(String log) {
		Main.server.visualLog(log);
	}
}
