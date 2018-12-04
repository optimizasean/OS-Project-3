//Operating Systems Project (osp) package
package osp;

import java.io.File;
import java.io.IOException;

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
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER + Constants.FILE_LOCAL_LOG);
            file.createNewFile();

            //PC1 
            file = new File(Constants.DIRECTORY_PATH_PC1);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC1 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            file = new File(Constants.DIRECTORY_PATH_PC1 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
            //Target File
            file = new File(Constants.TARGET_PATH);
            file.createNewFile();

            //PC2 
            file = new File(Constants.DIRECTORY_PATH_PC2);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC2 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            file = new File(Constants.DIRECTORY_PATH_PC2 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();

            //PC3 
            file = new File(Constants.DIRECTORY_PATH_PC3);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC3 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            file = new File(Constants.DIRECTORY_PATH_PC3 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();

            //PC4 
            file = new File(Constants.DIRECTORY_PATH_PC4);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC4 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            file = new File(Constants.DIRECTORY_PATH_PC4 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();

            //PC5 
            file = new File(Constants.DIRECTORY_PATH_PC5);
            file.mkdirs();
            file = new File(Constants.DIRECTORY_PATH_PC5 + Constants.FILE_GLOBAL_LOG);
            file.createNewFile();
            file = new File(Constants.DIRECTORY_PATH_PC5 + Constants.FILE_LOCAL_LOG);
            file.createNewFile();
        } catch (IOException iex) {
            System.out.println("Failure at Directory Structure");
            System.exit(1);
        }

        System.out.println("Directory Structure is good");
        return;
    }

    public void wipeFiles() {
        return;
    }
}