//Operating Systems Project (osp) package
package osp;

//File
import java.io.File;
import java.io.RandomAccessFile;

//Exceptions
import java.io.IOException;
import java.io.FileNotFoundException;

/****************************
 * DirectoryStructure class
 * ensures the directories and
 * files utilized by the program
 * all exist before atttempting to
 * read or write from them
 ****************************/
public class DirectoryStructure {
    private File file = null;

    //Blank Constructor
    public DirectoryStructure() {}

    //Create the directories and files
    public void ensureStructure() {
        try {
            //CONTROLLER
            //Ensure Controller Directory Existence
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER);
            file.mkdirs();
            //Ensure Controller Global Log File Existence
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);
            //Ensure Controller Local Log File Existence
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);

            //PC1 
            file = new File(Constants.DIRECTORY_PATH_PC1);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC1 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);
            file = new File(Constants.DIRECTORY_PATH_PC1 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);
            //Target File
            file = new File(Constants.TARGET_PATH);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);

            //PC2 
            file = new File(Constants.DIRECTORY_PATH_PC2);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC2 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);
            file = new File(Constants.DIRECTORY_PATH_PC2 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);

            //PC3 
            file = new File(Constants.DIRECTORY_PATH_PC3);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC3 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);
            file = new File(Constants.DIRECTORY_PATH_PC3 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);

            //PC4 
            file = new File(Constants.DIRECTORY_PATH_PC4);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC4 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);
            file = new File(Constants.DIRECTORY_PATH_PC4 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);

            //PC5 
            file = new File(Constants.DIRECTORY_PATH_PC5);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC5 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);
            file = new File(Constants.DIRECTORY_PATH_PC5 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            DirectoryStructure.wipeFile(file);
        } catch (IOException iex) {
            System.out.println("Failure at Directory Structure");
            System.exit(1);
        }

        System.out.println("Directory Structure is good");
        return;
    }

    public static void makeBackgroundLog() {
        try {
            //Background Log
            //Ensure directory exists
            File file = new File(Constants.BACKGROUND_LOG_PATH);
            file.mkdirs();
            //Ensure file exists
            file = new File(Constants.BACKGROUND_LOG_PATH + Constants.FILE_BACKGROUND_LOG);
            file.createNewFile();
            //Ensure file is wiped
            DirectoryStructure.wipeFile(file);
        } catch (IOException iex) {
            System.err.println("FAILED TO MAKE BACKGROUND LOG!");
            System.exit(1);
        }
    }

    public static void wipeFile(String path) {
        try {
            //RandomAccessFile to wipe file
            RandomAccessFile raf = new RandomAccessFile(path, "w");
            
            //Set length to 0 or wipe
            raf.setLength(0);
            //Close (no memory leak)
            raf.close();
        } catch (FileNotFoundException fnfex) {

        } catch (IOException iex) {}
        return;
    }
    public static void wipeFile(File file) {
        try {
            //RandomAccessFile to wipe file
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            
            //Set length to 0 or wipe
            raf.setLength(0);
            //Close (no memory leak)
            raf.close();
        } catch (FileNotFoundException fnfex) {

        } catch (IOException iex) {}
        return;
    }
}