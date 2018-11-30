//Operating Systems Project (osp) package
package osp;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerThread extends Thread {
    private Socket socket = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private VectorClock clock = null;
    private int id = 0;

    //Server link
    private Server server = null;
    
    ServerThread(Server server, Socket socket, int id, ObjectOutputStream oos, ObjectInputStream ois) {
        this.server = server;
        this.socket = socket;
        this.id = id;
        this.oos = oos;
        this.ois = ois;
        this.clock = new VectorClock(this.id);
    }
    
    public void run() {
        try {
			oos.writeObject(this.clock);
			
			while(true) {
				String msg = ois.readUTF();
				
				/////////////////////////////READ/////////////////////////////
				
				if(msg.equals("read_request")) {
					//send read request to PC1
					VectorClock temp = (VectorClock) ois.readObject();
					for(ServerThread st : Main.stv) {
						if(st.clock.ID == 1) {
							st.oos.writeUTF("read_request");
							st.oos.writeObject(temp);
							break;
						}
					}
				}
				if(msg.equals("read_reply")) {
					//send read reply to the PC that requested it
					VectorClock temp = (VectorClock) ois.readObject();
					VectorClock target = (VectorClock) ois.readObject();
					for(ServerThread st : Main.stv) {
						if(st.clock.ID == target.ID) {
							st.oos.writeUTF("read_reply");
							st.oos.writeObject(temp);
							break;
						}
					}
				}
				//////////////////////////////////////////////////////////////
				
				
				/////////////////////////////WRITE////////////////////////////
				
				if(msg.equals("write_request")) {
					//send write request to all other PCs
					VectorClock temp = (VectorClock) ois.readObject();
					for(ServerThread st : Main.stv) {
						if(st.clock.ID != temp.ID) {
							st.oos.writeUTF("write_request");
							st.oos.writeObject(temp);
						}
					}
				}
				
				if(msg.equals("write_reply")) {
					//send read reply to the PC that requested it
					VectorClock temp = (VectorClock) ois.readObject();
					VectorClock target = (VectorClock) ois.readObject();
					for(ServerThread st : Main.stv) {
						if(st.clock.ID == target.ID) {
							st.oos.writeUTF("write_reply");
							st.oos.writeObject(temp);
							break;
						}
					}
				}
				
				//////////////////////////////////////////////////////////////	
				
				if(msg.equals("quit")) {
					this.socket.close();
					break;
				}
				
			}
			
			oos.close();
			ois.close();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public void end() {
        try {
            this.socket.close();
        } catch (IOException iex) {
            System.err.println("Failure to end server");
        }
        
    }
}
