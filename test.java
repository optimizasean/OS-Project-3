import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        try {
            File file = new File("logs/CONTROLLER/");
            file.mkdirs();
            file = new File("logs/CONTROLLER/log.txt");
            file.createNewFile();
            file = new File("logs/PC1/");
            file.mkdirs();
            file = new File("logs/PC1/log.txt");
            file.createNewFile();
        } catch (IOException iex) {System.out.println("FUCK");}
    }
}