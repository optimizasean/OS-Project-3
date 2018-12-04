//Operating Systems Project (osp) package
package osp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**************************
 * Read class is used by the
 * client class to read from
 * the file in PC1
 **************************/
public class Read {
	public static void makeCopy() throws IOException {
		File source = new File("./data/PC1/target.txt");
		File destination = new File("./data/PC1/targetReadOnly.txt");
		Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static void deleteCopy() throws IOException {
		File file = new File("./data/PC1/targetReadOnly.txt");
		file.delete();
	}
	
	
}