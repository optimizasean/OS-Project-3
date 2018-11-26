import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Log {
	
	public static void write(int ID, String message) throws IOException {
		String dir;
		if(ID == 0) dir = "./data/CONTROLLER/log.txt";
		else dir = "./data/PC"+ ID + "/log.txt";
		
		RandomAccessFile raf = new RandomAccessFile(dir, "rw");
		raf.seek(raf.length());
		raf.writeBytes(message+"\n");
		raf.close();
	}
	
	public static void cleanup() throws FileNotFoundException {
		for(int i=1; i<6; i++) {
			PrintWriter pw = new PrintWriter("./data/PC"+i+"/log.txt");
		}
	}
}
