//Operating Systems Project (osp) package
package osp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

//Date and timestamping
import java.util.Date;
import java.text.SimpleDateFormat;


public class GlobalLogger {
	//TimeStamp
	private static String timeStamp = null;

	//Outdated
	public static void writePC(VectorClock clock, String message) throws IOException {
		String dir = "./data/PC" + clock.ID + "/" + Constants.FILE_GLOBAL_LOG;

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile(dir, "rw");
		
		raf.seek(raf.length());//move cursor to end of file
		raf.writeBytes(clock + "\t" + message + "\n");//write message at end of file
		raf.close();
	}

	public static void writeController(VectorClock clock, String message) throws IOException {
		String dir = Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_GLOBAL_LOG;

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile(dir, "rwd"); //rwd guarantees data written to file synchronously on device storage
		
		raf.seek(raf.length());//move cursor to end of file
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		raf.writeBytes("[" + timeStamp + "] " + clock + "\t" + message + "\n");//write message at end of file
		raf.close();
	}

	public static void writeBackground(String message) throws IOException {
		String dir = Constants.BACKGROUND_LOG_PATH + Constants.FILE_BACKGROUND_LOG;

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile(dir, "rwd"); //rwd guarantees data written to file synchronously on device storage
		
		raf.seek(raf.length());//move cursor to end of file
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		raf.writeBytes("[" + timeStamp + "]\t" + message + "\n");//write message at end of file
		raf.close();
	}
	
	//NEEDS UPDATING ------------------------- OUTDATED
	/*public static void cleanup() throws FileNotFoundException {
		PrintWriter cntrl = new PrintWriter("./data/CONTROLLER/log.txt");
		cntrl.close();
		for(int i = 1; i < 6; i++) {
			PrintWriter pw = new PrintWriter("./data/PC" + i + "/" + Constants.FILE_GLOBAL_LOG);
		}
	}*/
}
