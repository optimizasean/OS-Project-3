//Operating Systems Project (osp) package
package osp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Read {

	public static void makeCopy() throws IOException {
		File source = new File("./data/PC1/target.txt");
		File destination = new File("./data/PC1/targetReadOnly.txt");
		Files.copy(source.toPath(), destination.toPath());
	}
	
	public static void deleteCopy() throws IOException {
		File file = new File("./data/PC1/targetReadOnly.txt");
		file.delete();
	}
	
	
}