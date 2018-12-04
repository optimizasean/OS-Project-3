//Operating Systems Project (osp) package
package osp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class GlobalLogger {
	private static String dir = null;
	//TimeStamp
	private static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

	public static synchronized void write(VectorClock clock, String message) throws IOException {
		if (clock.ID == 0) dir = Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_GLOBAL_LOG;
		else dir = "./data/PC" + clock.ID + "/" + Constants.FILE_GLOBAL_LOG;

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
		raf.writeBytes(clock.print()+"\t"+message+"\n");//write message at end of file
		lock.release();//release exclusive lock
		raf.close();
		ch.close();
	}
	
	public static void cleanup() throws FileNotFoundException {
		PrintWriter cntrl = new PrintWriter("./data/CONTROLLER/log.txt");
		cntrl.close();
		for(int i = 1; i < 6; i++) {
			PrintWriter pw = new PrintWriter("./data/PC" + i + "/" + Constants.FILE_GLOBAL_LOG);
		}
	}
}
