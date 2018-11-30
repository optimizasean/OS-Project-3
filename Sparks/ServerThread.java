package osp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ServerThread extends Thread{
	
	final Socket s;
	final VectorClock clock;
	final ObjectOutputStream oos;
	final ObjectInputStream ois;
	final DataOutputStream dos;
	final DataInputStream dis;
	
	
	public ServerThread(Socket s, int ID, ObjectOutputStream oos, ObjectInputStream ois, DataOutputStream dos, DataInputStream dis) {
		this.s = s;
		this.clock = new VectorClock(ID);
		this.oos = oos;
		this.ois = ois;
		this.dos = dos;
		this.dis = dis;
		
	}
	
	public void run() {
		
		try {
			oos.writeObject(this.clock);
			
			while(true) {
				String msg = dis.readUTF();
				
				/////////////////////////////READ/////////////////////////////
				
				if(msg.equals("read_request")) {
					//send read request to PC1
					VectorClock temp = (VectorClock) ois.readObject();
					for(ServerThread st : Server.stv) {
						if(st.clock.ID == 1) {
							st.dos.writeUTF("read_request");
							st.oos.writeObject(temp);
							break;
						}
					}
				}
				if(msg.equals("read_reply")) {
					//send read reply to the PC that requested it
					VectorClock temp = (VectorClock) ois.readObject();
					VectorClock target = (VectorClock) ois.readObject();
					for(ServerThread st : Server.stv) {
						if(st.clock.ID == target.ID) {
							st.dos.writeUTF("read_reply");
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
					for(ServerThread st : Server.stv) {
						if(st.clock.ID != temp.ID) {
							st.dos.writeUTF("write_request");
							st.oos.writeObject(temp);
						}
					}
				}
				
				if(msg.equals("write_reply")) {
					//send read reply to the PC that requested it
					VectorClock temp = (VectorClock) ois.readObject();
					VectorClock target = (VectorClock) ois.readObject();
					for(ServerThread st : Server.stv) {
						if(st.clock.ID == target.ID) {
							st.dos.writeUTF("write_reply");
							st.oos.writeObject(temp);
							break;
						}
					}
				}
				
				//////////////////////////////////////////////////////////////	
				
				if(msg.equals("quit")) {
					this.s.close();
					break;
				}
				
			}
			
			oos.close();
			ois.close();
			dos.close();
			dis.close();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
