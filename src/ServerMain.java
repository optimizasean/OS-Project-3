//Operating Systems Project (osp) package
package osp;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class ServerMain implements Runnable {
		private ServerSocket server = null;
		private Socket client = null;

		public void run() {
			try {
				server = new ServerSocket(port);
			} catch (IOException iex) {
				System.out.println("Failed to start server");
			}
			
			try {
				while(true) {
					client = server.accept();
					Thread t = new ServerThread(client);
					t.start();
				}
			} catch (IOException iex) {}
		}
	}