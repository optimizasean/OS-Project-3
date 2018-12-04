//Operating Systems Project (osp) package
package osp;

//Exceptions
import java.io.IOException;
import java.io.FileNotFoundException;

//Files and writing
import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

//Date and timestamping
import java.util.Date;
import java.text.SimpleDateFormat;

/*****************************************************************\
 * LocalLogger class is used to log things locally without worry of
 * synchonicity and logs based on timeStamp
 * 
 * @author Shadowbomb
\*****************************************************************/
public class LocalLogger {
    private PrintWriter logger = null;
    private File file = null;
    private String timeStamp = null;

    /*****************************************************************\
     * LocalLogger {@link #LocalLogger(File) LocalLogger} sets up the logger based on the File
     * passed in.
     * 
     * @exception FileNotFoundException
     * @exception IOException
     * @return LocalLogger
    \*****************************************************************/
    public LocalLogger(File file) {
        try {
            this.file = file;
            this.logger = new PrintWriter(new BufferedWriter(new FileWriter(this.file, true)));
        } catch (FileNotFoundException fnfx) {
            fnfx.printStackTrace();
            System.err.println("Logger Failure: File not Found");
            System.out.println(fnfx.getMessage());
            System.err.println("SHUTTING DOWN");
            System.exit(0);
        } catch (IOException iex) {
            iex.printStackTrace();
            System.err.println("Logger Failure: IO?");
            System.out.println(iex.getMessage());
            System.err.println("SHUTTING DOWN");
            System.exit(0);
        }
    }

    /*****************************************************************\
     * LocalLogger {@link #log(String) log} method takes a String
     * and logs it to the file with the current timestamp.
     * 
     * @return void
    \*****************************************************************/
    public void log(String log) {
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        this.logger.println("[" + timeStamp + "] " + log);
        return;
    }

    /*****************************************************************\
     * LocalLogger {@link #close() close} method closes the logger and
     * saves the file.
     * 
     * @return void
    \*****************************************************************/
    public void close() {
        this.logger.close();
        return;
    }
}