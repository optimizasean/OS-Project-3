//Operating Systems Project (osp) package
package osp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

//Date and timestamping
import java.util.Date;
import java.text.SimpleDateFormat;


public class GlobalLogger {
	//TimeStamp
	private static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

	public static synchronized void write(VectorClock clock, String message) throws IOException {
		String dir = "./data/PC" + clock.ID + "/" + Constants.FILE_GLOBAL_LOG;
		String dir2 = Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_GLOBAL_LOG;

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile(dir, "rw");
		
		raf.seek(raf.length());//move cursor to end of file
		raf.writeBytes("[" + timeStamp + "] "+clock.print()+"\t"+message+"\n");//write message at end of file
		raf.close();

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf2 = new RandomAccessFile(dir2, "rw");
	
		raf2.seek(raf2.length());//move cursor to end of file
		raf2.writeBytes("[" + timeStamp + "] "+clock.print()+"\t"+message+"\n");//write message at end of file
		raf2.close();
	}
	
	public static void cleanup() throws FileNotFoundException {
		PrintWriter cntrl = new PrintWriter("./data/CONTROLLER/log.txt");
		cntrl.close();
		for(int i = 1; i < 6; i++) {
			PrintWriter pw = new PrintWriter("./data/PC" + i + "/" + Constants.FILE_GLOBAL_LOG);
		}
	}
}
