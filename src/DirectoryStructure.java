//Operating Systems Project (osp) package
package osp;

//File
import java.io.File;
import java.io.RandomAccessFile;

//Exceptions
import java.io.IOException;
import java.io.FileNotFoundException;

/*****************************************************************\
 * DirectoryStructure class ensures the directories and files
 * utilized by the program all exist before atttempting to
 * read or write from them.
 * 
 * @author Shadowbomb
\*****************************************************************/
public class DirectoryStructure {
    private File file = null;

    /*****************************************************************\
     * DirectoryStructure {@link #DirectoryStructure() Constructor}
     * is a blank constructor meant for creating the object that will
     * ensure the directories and files necessary for the program to
     * ensure their existence before attempting to read, write, or open
     * to save from exceptions.
     * 
     * @return DirectoryStructure Object
    \*****************************************************************/
    public DirectoryStructure() {}

    /*****************************************************************\
     * DirectoryStructure {@link #ensureStructure() ensureStructure} is
     * a blank constructor meant for creating the directories and files
     * necessary for the program to ensure their existence before
     * attempting to read, write, or open to save from exceptions
     * utilizing the {@link osp.Constants Constants} paths and filenames
     * and Implements {@link #wipeFile(File) wipeFile} to guarantee
     * the files are empty before running so logs are only descriptive
     * of the current session.
     * 
     * @exception FileNotFoundException
     * @exception IOException
     * @return void
     * @see {@link osp.Constants Constants}
     * @see {@link #wipeFile(File)}
    \*****************************************************************/
    public void ensureStructure() {
        Main.log("[DirectoryStructure] Ensuring Directory Structure");
        try {
            //CONTROLLER
            //Ensure Controller Directory Existence
            Main.log("[DirectoryStructure] Ensuring Controller");
            Main.log("[DirectoryStructure] Ensuring directories for Controller");
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER);
            file.mkdirs();
            Main.log("[DirectoryStructure] Ensured Directories created or exists");
            //Ensure Controller Global Log File Existence
            Main.log("[DirectoryStructure] Ensuring controller global log");
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] Controller global log created or exists");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping controller global log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] Controller global log wiped");
            //Ensure Controller Local Log File Existence
            Main.log("[DirectoryStructure] Ensuring controller local log");
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] Controller local log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping controller local log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] Controller local log wiped");

            //PC1
            Main.log("[DirectoryStructure] Ensuring PC1");
            //Directory Structure
            Main.log("[DirectoryStructure] Ensuring directories for PC1");
            file = new File(Constants.DIRECTORY_PATH_PC1);
            file.mkdirs();
            Main.log("[DirectoryStructure] Directories created or exists");
            //Global log
            Main.log("[DirectoryStructure] Ensuring PC1 global log");
            file = new File(Constants.DIRECTORY_PATH_PC1 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] PC1 global log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC1 global log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC1 global log wiped");
            //Local log
            Main.log("[DirectoryStructure] Ensuring PC1 local log");
            file = new File(Constants.DIRECTORY_PATH_PC1 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] PC1 local log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC1 local log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC1 local log wiped");
            //Target File
            Main.log("[DirectoryStructure] Ensuring target file");
            file = new File(Constants.TARGET_PATH);
            file.createNewFile();
            Main.log("[DirectoryStructure] Target file created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping target file");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] Target file wiped");

            //PC2
            Main.log("[DirectoryStructure] Ensuring PC2");
            //Directory structure
            Main.log("[DirectoryStructure] Ensuring directories for PC2");
            file = new File(Constants.DIRECTORY_PATH_PC2);
            file.mkdirs();
            Main.log("[DirectoryStructure] Directories created or exists");
            //Global log
            Main.log("[DirectoryStructure] Ensuring PC2 global log");
            file = new File(Constants.DIRECTORY_PATH_PC2 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] PC2 global log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC2 global log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC2 global log wiped");
            //local log
            Main.log("[DirectoryStructure] Ensuring PC2 local log");
            file = new File(Constants.DIRECTORY_PATH_PC2 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] PC2 local log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC2 local log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC2 local log wiped");

            //PC3
            Main.log("[DirectoryStructure] Ensuring PC3");
            //Directory structure
            Main.log("[DirectoryStructure] Ensuring directories for PC3");
            file = new File(Constants.DIRECTORY_PATH_PC3);
            file.mkdirs();
            Main.log("[DirectoryStructure] Directories created or exists");
            //Global log
            Main.log("[DirectoryStructure] Ensuring PC3 global log");
            file = new File(Constants.DIRECTORY_PATH_PC3 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC3 global log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC3 global log wiped");
            //local log
            Main.log("[DirectoryStructure] Ensuring PC3 local log");
            file = new File(Constants.DIRECTORY_PATH_PC3 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC3 local log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC3 local log wiped");

            //PC4
            Main.log("[DirectoryStructure] Ensuring PC4");
            //Directory structure
            Main.log("[DirectoryStructure] Ensuring directories for PC4");
            file = new File(Constants.DIRECTORY_PATH_PC4);
            file.mkdirs();
            Main.log("[DirectoryStructure] Directories created or exists");
            //Global log
            Main.log("[DirectoryStructure] Ensuring PC4 global log");
            file = new File(Constants.DIRECTORY_PATH_PC4 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] PC4 global log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC4 global log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC4 global log wiped");
            //local log
            Main.log("[DirectoryStructure] Ensuring PC4 local log");
            file = new File(Constants.DIRECTORY_PATH_PC4 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Ensure File Empty
            Main.log("[DirectoryStructure] PC4 local log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC4 local log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC4 local log wiped");

            //PC5
            Main.log("[DirectoryStructure] Ensuring PC5");
            //Directory structure
            Main.log("[DirectoryStructure] Ensuring directories for PC5");
            file = new File(Constants.DIRECTORY_PATH_PC5);
            file.mkdirs();
            Main.log("[DirectoryStructure] Directories created or exists");
            //Global log
            Main.log("[DirectoryStructure] Ensuring PC5 global log");
            file = new File(Constants.DIRECTORY_PATH_PC5 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] PC5 global log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC5 global log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC5 global log wiped");
            //local log
            Main.log("[DirectoryStructure] Ensuring PC5 local log");
            file = new File(Constants.DIRECTORY_PATH_PC5 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            Main.log("[DirectoryStructure] PC5 local log created or existed");
            //Ensure File Empty
            Main.log("[DirectoryStructure] Wiping PC5 local log");
            DirectoryStructure.wipeFile(file);
            Main.log("[DirectoryStructure] PC5 local log wiped");
        } catch (IOException iex) {
            System.out.println("Failure at Directory Structure");
            System.exit(1);
        }

        System.out.println("Directory Structure is good");
        return;
    }

    /*****************************************************************\
     * DirectoryStructure {@link #makeBackgroundLog() makeBackgroundLog}
     * is a method which creates the main log used in the Program.  It
     * utilizes the {@link Constants Constants} class for file names and
     * paths and utilized {@link #wipeFile(File) wipeFile} for
     * clearing the files so the log is only representative of the
     * current session.
     * 
     * @exception FileNotFoundException
     * @exception IOException
     * @return void
     * @see {@link Constants Constants}
     * @see {@link #wipeFile(File) wipeFile}
    \*****************************************************************/
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

    /*****************************************************************\
     * DirectoryStructure {@link #wipeFile(String) wipeFile} is used to
     * wipe files according to the path by truncating their size to 0.
     * 
     * @exception FileNotFoundException
     * @exception IOException
     * @return void
    \*****************************************************************/
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
    /*****************************************************************\
     * DirectoryStructure {@link #wipeFile(File) wipeFile} is used
     * to wipe files according to the File Object by truncating their
     * size to 0.
     * 
     * @exception FileNotFoundException
     * @exception IOException
     * @return void
    \*****************************************************************/
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