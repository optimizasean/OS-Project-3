package osp;

import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class LocalLogger {
    private PrintWriter logger = null;
    private File file = null;
    private String timeStamp = null;

    public LocalLogger(File file) {
        try {
            this.file = file;
            this.logger = new PrintWriter(new BufferedWriter(new FileWriter(this.file, true)));
            timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
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

    public void log(String log) {
        this.logger.println("[" + timeStamp + "] " + log);
        return;
    }

    public void close() {
        this.logger.close();
        return;
    }
}