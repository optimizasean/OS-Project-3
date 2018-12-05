//Operating Systems Project (osp) package
package osp;

//Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

//Date and timestamping
import java.util.Date;
import java.text.SimpleDateFormat;

//File Reading and Writing
import java.io.PrintWriter;
import java.io.RandomAccessFile;

/*****************************************************************\
 * GlobalLogger Class is used to log events on any file and works
 * synchronously.
 * 
 * @author Christopher-Sparks
 * @author Shadowbomb
\*****************************************************************/
public class GlobalLogger {
	//TimeStamp
	private static String timeStamp = null;

	/*****************************************************************\
	 * GlobalLogger {@link #writePC(VectorClock, String) writePC} is
	 * used to write to the PC log found by the
	 * {@link osp.Constants Constants} path.  It implements the
	 * {@link osp.VectorClock VectorClock} to get an accurate distributed
	 * systems clock for event timing and follows the paths and filenames
	 * in the {@link osp.Constants Constants} interface. It uses the
	 * {@link osp.VectorClock.toString() VectorClock.toString()} method
	 * to print it to the log.
	 * 
	 * @exception FileNotFoundException
	 * @throws IOException
	 * @return void
	 * @see {@link osp.Constants Constants}
	 * @see {@link osp.VectorClock#toString() VectorClock}
	\*****************************************************************/
	public static void writePC(VectorClock clock, String message) throws IOException {
		if (clock == null) {
			System.err.println("NULL CLOCK: " + message);
			return;
		}
		String dir = "./data/PC" + clock.ID + "/" + Constants.FILE_GLOBAL_LOG;

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile(dir, "rw");
		
		raf.seek(raf.length());//move cursor to end of file
		raf.writeBytes(clock + "\t" + message + "\n");//write message at end of file
		raf.close();
	}

	/*****************************************************************\
	 * GlobalLogger {@link #writeController(VectorClock, String) writeController}
	 * is used to write to the controller file which contains the list
	 * of all events that have happened. This writes synchronously to
	 * the controller file and records the current timeStamp and the
	 * {@link osp.VectorClock VectorClock} class and uses the filenames
	 * and paths in the {@link osp.Constants Constants} interface.
	 * 
	 * @exception FileNotFoundException
	 * @throws IOException
	 * @return void
	 * @see {@link osp.Constants}
	 * @see {@link osp.VectorClock#toString() VectorClock}
	\*****************************************************************/
	public static void writeController(VectorClock clock, String message) throws IOException {
		if (clock == null) {
			System.err.println("NULL CLOCK: " + message);
			return;
		}
		String dir = Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_GLOBAL_LOG;

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile(dir, "rwd"); //rwd guarantees data written to file synchronously on device storage
		
		raf.seek(raf.length());//move cursor to end of file
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		raf.writeBytes("[" + timeStamp + "] " + clock + "\t" + message + "\n");//write message at end of file
		raf.close();
	}

	/*****************************************************************\
	 * GlobalLogger {@link #writeBackground(String) writeBackground}
	 * is used to log events on any file and works synchronously using
	 * the {@link osp.Constants Constants} interface for the file names
	 * and paths.
	 * 
	 * @exception FileNotFoundException
	 * @throws IOException
	 * @return void
	 * @see {@link osp.Constants Constants}
	\*****************************************************************/
	public static void writeBackground(String message) throws IOException {
		String dir = Constants.BACKGROUND_LOG_PATH + Constants.FILE_BACKGROUND_LOG;

		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile(dir, "rwd"); //rwd guarantees data written to file synchronously on device storage
		
		raf.seek(raf.length());//move cursor to end of file
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		raf.writeBytes("[" + timeStamp + "]\t" + message + "\n");//write message at end of file
		raf.close();
	}
	
	/*****************************************************************\
	 * GlobalLogger {@link #cleanup() cleanup} is used to erase the
	 * controller log file and the pc log files. It uses the
	 * {@link osp.Constants Constants} class for the file names and
	 * paths.
	 * 
	 * @exception FileNotFoundException
	 * @throws IOException
	 * @return void
	 * @see {@link osp.Constants Constants}
	 * @deprecated
	\*****************************************************************/
	public static void cleanup() throws FileNotFoundException {
		PrintWriter cntrlG = new PrintWriter(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_GLOBAL_LOG);
		cntrlG.close();
		PrintWriter cntrlL = new PrintWriter(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_LOCAL_LOG);
		cntrlL.close();
		PrintWriter pc1G = new PrintWriter(Constants.DIRECTORY_PATH_PC1 + Constants.FILE_GLOBAL_LOG);
		pc1G.close();
		PrintWriter pc1L = new PrintWriter(Constants.DIRECTORY_PATH_PC1 + Constants.FILE_LOCAL_LOG);
		pc1L.close();
		PrintWriter pc2G = new PrintWriter(Constants.DIRECTORY_PATH_PC2 + Constants.FILE_GLOBAL_LOG);
		pc2G.close();
		PrintWriter pc2L = new PrintWriter(Constants.DIRECTORY_PATH_PC2 + Constants.FILE_LOCAL_LOG);
		pc2L.close();
		PrintWriter pc3G = new PrintWriter(Constants.DIRECTORY_PATH_PC3 + Constants.FILE_GLOBAL_LOG);
		pc3G.close();
		PrintWriter pc3L = new PrintWriter(Constants.DIRECTORY_PATH_PC3 + Constants.FILE_LOCAL_LOG);
		pc3L.close();
		PrintWriter pc4G = new PrintWriter(Constants.DIRECTORY_PATH_PC4 + Constants.FILE_GLOBAL_LOG);
		pc4G.close();
		PrintWriter pc4L = new PrintWriter(Constants.DIRECTORY_PATH_PC4 + Constants.FILE_LOCAL_LOG);
		pc4L.close();
		PrintWriter pc5G = new PrintWriter(Constants.DIRECTORY_PATH_PC5 + Constants.FILE_GLOBAL_LOG);
		pc5G.close();
		PrintWriter pc5L = new PrintWriter(Constants.DIRECTORY_PATH_PC5 + Constants.FILE_LOCAL_LOG);
		pc5L.close();
		return;
	}
}
