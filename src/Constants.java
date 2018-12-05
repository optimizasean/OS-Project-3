//Operating Systems Project (osp) package
package osp;

/*****************************************************************\
 * Constants interface for constant reference points for easy
 * change with regards to conventions and program data, names,
 * and other things.
 * 
 * @author Shadowbomb
\*****************************************************************/
public interface Constants {
    //Client Number
    public static final int NUMBER_OF_CLIENTS = 5;

    //Port Default
    public static final int PORT_DEFAULT = 9001;

    //Defaults - READ/WRITE
    public static final int READ_DEFAULT = 80;
    public static final int WRITE_DEFAULT = 20;
    
    //File Names
    public static final String FILE_GLOBAL_LOG = "global_log.txt";
    public static final String FILE_LOCAL_LOG = "local_log.txt";
    public static final String FILE_TARGET = "target.txt";
    public static final String FILE_BACKGROUND_LOG = "background_log.txt";

    //Directory Paths
    public static final String DIRECTORY_PATH_CONTROLLER = "data/CONTROLLER/";
    public static final String DIRECTORY_PATH_PC1 = "data/PC1/";
    public static final String DIRECTORY_PATH_PC2 = "data/PC2/";
    public static final String DIRECTORY_PATH_PC3 = "data/PC3/";
    public static final String DIRECTORY_PATH_PC4 = "data/PC4/";
    public static final String DIRECTORY_PATH_PC5 = "data/PC5/";

    //Target File Path
    public static final String TARGET_PATH = "data/PC1/target.txt";

    //Background Log Path
    public static final String BACKGROUND_LOG_PATH = "data/";
}