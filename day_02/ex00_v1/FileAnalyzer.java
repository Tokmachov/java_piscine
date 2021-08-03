import java.io.File;
import java.util.LinkedList;

public class FileAnalyzer {
    public static void main(String[] av) {
        if (av.length != 1) {
            System.err.println("Error: please provide one argument - path to signatures.txt file");
            System.exit(-1);
        }
        
    }
    private LinkedList<FileType> fileTypesList;
}
