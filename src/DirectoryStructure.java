//Operating Systems Project (osp) package
package osp;

import java.io.File;
import java.io.IOException;

public class DirectoryStructure {
    private File file = null;

    //Blank Constructor
    public DirectoryStructure() {}

    //Create the directories and files
    public static void run() {
        try {
            //CONTROLLER
            file = new File(Constants.DIRECTORY_PATH_CONTROLLER);
            file.mkdirs();
            file = new File(Constants.LOG_PATH_CONTROLLER);
            file.createNewFile();

            //PC1 
            file = new File(Constants.DIRECTORY_PATH_PC1);
            file.mkdirs();
            file = new File(Constants.LOG_PATH_PC1);
            file.createNewFile();
            //Target File
            file = new File(Constants.TARGET_PATH);
            file.createNewFile();

            //PC2 
            file = new File(Constants.DIRECTORY_PATH_PC2);
            file.mkdirs();
            file = new File(Constants.LOG_PATH_PC2);
            file.createNewFile();

            //PC3 
            file = new File(Constants.DIRECTORY_PATH_PC3);
            file.mkdirs();
            file = new File(Constants.LOG_PATH_PC3);
            file.createNewFile();

            //PC4 
            file = new File(Constants.DIRECTORY_PATH_PC4);
            file.mkdirs();
            file = new File(Constants.LOG_PATH_PC4);
            file.createNewFile();

            //PC5 
            file = new File(Constants.DIRECTORY_PATH_PC5);
            file.mkdirs();
            file = new File(Constants.LOG_PATH_PC5);
            file.createNewFile();
        } catch (IOException iex) {
            System.out.println("Failure at Directory Structure");
            System.exit(1);
        }

        System.out.println("Directory Structure is good");
    }
}