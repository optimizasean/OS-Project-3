//Operating Systems Project (osp) package
package osp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.FileChannel;
import java.io.FileLock;

public class GlobalLogger {
	
	public static void write(int ID, String message) throws IOException {
		String dir;
		if (ID == 0) dir = Constants.DIRECTORY_PATH_CONTROLLER;
		else dir = "./data/PC" + ID + "/" + Constants.FILE_LOG;

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile(dir, "rw");

		//get channel for the file
		FileChannel ch = raf.getChannel();
		
		//create file lock
		FileLock lock = null;
		
		try {
			lock = ch.tryLock();//acquire an exclusive lock to this channel's file
		} catch (IOException e) {
			raf.close();
			ch.close();
		}
		
		raf.seek(raf.length());//move cursor to end of file
		raf.writeBytes(message);//write message at end of file
		lock.release();//release exclusive lock
		raf.close();
		ch.close();
	}
	
	public static void cleanup() throws FileNotFoundException {

		for(int i = 1; i < 6; i++) {
			PrintWriter pw = new PrintWriter("./data/PC" + i + "/" + Constants.FILE_LOG);
		}
	}
}
