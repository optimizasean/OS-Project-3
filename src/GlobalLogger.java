//Operating Systems Project (osp) package
package osp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class GlobalLogger {
	
	public static void write(int ID, String message) throws IOException {
		String dir;
		if (ID == 0) dir = Constants.DIRECTORY_PATH_CONTROLLER;
		else dir = "./data/PC" + ID + "/" Constants.FILE_LOG;
		
		RandomAccessFile raf = new RandomAccessFile(dir, "rw");
		raf.seek(raf.length());
		raf.writeBytes(message + "\n");
		raf.close();
	}
	
	public static void cleanup() throws FileNotFoundException {
		for(int i = 1; i < 6; i++) {
			PrintWriter pw = new PrintWriter("./data/PC" + i + Constants.FILE_LOG);
		}
	}
}
