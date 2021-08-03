import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileWordByWordScanner {

    private BufferedReader br;
    private Scanner sc;
    
    private static BufferedReader createBufferedReader(String filePath) throws FileNotFoundException {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            return br;
    }
    public FileWordByWordScanner(String filePath) throws FileNotFoundException {
        this.sc = new Scanner("");
        this.br = createBufferedReader(filePath);
    }
    public boolean hasNextWord() {
        if (sc.hasNext())
            return true;
        else
        {
            String line = null; 
            try {
                line = "";
                while (line != null && line.isEmpty())
                    line = br.readLine();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            if (line != null)
            {
                sc = new Scanner(line);
                return sc.hasNext();
            }
            else
                return false;
        }
    }
    public String nextWord() {
        return sc.next();
    }
    public void close() {
        try {
            br.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}