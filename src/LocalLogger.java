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

/*******************************
 * LocalLogger class used to log
 * to the local file of the client
 * or controller individually to
 * avoid performance hits from a
 * global logger and for better
 * time accuracy.
 *******************************/
public class LocalLogger {
    private PrintWriter logger = null;
    private File file = null;
    private String timeStamp = null;

    //LocalLogger Constructor sets up the logger
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

    //Log something
    public void log(String log) {
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        this.logger.println("[" + timeStamp + "] " + log);
        return;
    }

    //Saves and closes the log
    public void close() {
        this.logger.close();
        return;
    }
}