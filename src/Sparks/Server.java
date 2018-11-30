package osp;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Server {
	
	static Vector<ServerThread> stv = new Vector<>();
	static int i = 1;
	
	public Server() throws IOException {
		
		ServerSocket server = new ServerSocket(9001);
		System.out.println("Server started");
		System.out.println("Waiting for client");
		
		while(true) {
			Socket s = null;
			
			try {
				s = server.accept();
				System.out.println("PC"+i+" Accepted");
				
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				DataInputStream dis = new DataInputStream(s.getInputStream());
				
				ServerThread st = new ServerThread(s, i, oos, ois, dos, dis);
				Thread t = new Thread(st);
				stv.add(st);
				t.start();
				
				i++;
			}
			catch(Exception e) {
				server.close();
				e.printStackTrace();
			}
		}		
	}
}

