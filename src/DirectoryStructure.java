package osp;

import java.io.File;
import java.io.IOException;

public class DirectoryStructure {
    private File file = null;

    private final String path = "data/";
    private final String con = "CONTROLLER/";
    private final String pc1 = "PC_1/";
    private final String pc2 = "PC_2/";
    private final String pc3 = "PC_3/";
    private final String pc4 = "PC_4/";
    private final String pc5 = "PC_5/";
    private final String name = "log.txt";
    private final String target = "target.txt";

    //Blank Constructor
    public DirectoryStructure() {}

    //Create the directories and files
    public void run() {
        try {
            //CONTROLLER
            file = new File(path + con);
            file.mkdirs();
            file = new File(path + con + name);
            file.createNewFile();

            //PC1 
            file = new File(path + pc1);
            file.mkdirs();
            file = new File(path + pc1 + name);
            file.createNewFile();
            //Target File
            file = new File(path + pc1 + target);
            file.createNewFile();

            //PC2 
            file = new File(path + pc2);
            file.mkdirs();
            file = new File(path + pc2 + name);
            file.createNewFile();

            //PC3 
            file = new File(path + pc3);
            file.mkdirs();
            file = new File(path + pc3 + name);
            file.createNewFile();

            //PC4 
            file = new File(path + pc4);
            file.mkdirs();
            file = new File(path + pc4 + name);
            file.createNewFile();

            //PC5 
            file = new File(path + pc5);
            file.mkdirs();
            file = new File(path + pc5 + name);
            file.createNewFile();
        } catch (IOException iex) {
            System.out.println("Failure at Directory Structure");
            System.exit(1);
        }

        System.out.println("Directory Structure is good");
    }
}