//Operating Systems Project (osp) package
package osp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**********************
 * Write class is used by the
 * Client to write to the file
 * in PC1
 **********************/
public class Write {
	public static void downloadFile(VectorClock clock) throws IOException {
		File source = new File("./data/PC1/target.txt");
		File destination = new File("./data/PC" + clock.ID + "/target.txt");
		Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static void returnFile (VectorClock clock) throws IOException {
		File source = new File("./data/PC" + clock.ID + "/target.txt");
		File destination = new File("./data/PC1/target.txt");
		Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
		if(!source.equals(destination)) {
			source.delete();
		}
	}
	
	public static void deleteFile(VectorClock clock) throws IOException {
		File file = new File ("./data/PC" + clock.ID + "/target.txt");
		file.delete();
	}
	
	public static void writeFile(VectorClock clock) throws IOException {
		
		//RandomAccessFile used to write to certain area of file
		RandomAccessFile raf = new RandomAccessFile("./data/PC" + clock.ID + "/target.txt", "rw");
		
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
		raf.writeBytes("PC"+clock.ID+" write to file\n");//write message at end of file
		lock.release();//release exclusive lock
		raf.close();
		ch.close();
	}
	
	public static void cleanFile() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("./data/PC1/target.txt");
		pw.close();
	}
}
