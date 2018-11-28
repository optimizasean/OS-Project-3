//Operating Systems Project (osp) package
package osp;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;



public class Write {
	
	/**
	 * This method copies the target file from one PC directory to another
	 * directory. 
	 * 
	 * @param srcID - The PC that contains target.txt
	 * @param dstID - The PC that you want to contain target.txt
	 * @throws IOException
	 */
	public static void copyFile(int srcID, int dstID) throws IOException {
		File source = new File("./Data/PC" + srcID + "/" + Constants.FILE_TARGET);
		File destination = new File("./Data/PC" + dstID + "/" + Constants.FILE_TARGET);
		Files.copy(source.toPath(), destination.toPath());
	}
	
	
	/**
	 * This method is used to write a string message to the last line of target file
	 * The PC folder that uses this method must contain the target file or an exception 
	 * will be thrown. 
	 * 
	 * @param ID - The PC that contains target.txt
	 * @param message - The message that will be written to the end of the file
	 * @throws IOException
	 */
	public static void writeFile(int ID, String message) throws IOException {
		
		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile("./Data/PC" + ID + "/" + Constants.FILE_TARGET, "rw");
		
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
	
	
}
