package osp;

import java.io.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    
    ServerThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {}
    
    public void stop() {
        this.socket.close();
    }
}