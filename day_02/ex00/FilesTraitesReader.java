import java.io.FileInputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.lang.Integer;

public class FilesTraitesReader {
    private final static String SIGNATURES_PATH = "signatures.txt";
    
    public static LinkedList<FileTypeTraits> readFileTypeTraits() {
        LinkedList<FileTypeTraits> typesTraits = new LinkedList<>();
        FileInputStream fis = createFileInputStream();
        if (fis == null)
            return null;
        Scanner sc = new Scanner(fis);
        while (sc.hasNextLine())
        {
            String line = sc.nextLine();
            LinkedList<Integer> signatures = parseSignatures(line);
            String fileTypeName = parseFileTypeName(line);
            if (fileTypeName == null || signatures == null)
            {
                closeStream(fis);        
                return null;
            }
            FileTypeTraits traits = new FileTypeTraits(fileTypeName, signatures);
            typesTraits.add(traits);
        }
        closeStream(fis);
        return typesTraits;
    }
    private static LinkedList<Integer> parseSignatures(String s) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        Scanner sc = new Scanner(s);
        if (sc.hasNext())
            sc.next();
        else
            return null;
        while (sc.hasNext())
        {
            String hexStr = sc.next();
            try {
                int hexNum = Integer.parseInt(hexStr, 16);
                list.add(new Integer(hexNum));
            } catch (Exception ex) {
                return null;
            }
        }
        if (list.size() == 0)
            return null;
        return list;
    }
    private static String parseFileTypeName(String s) {
        Scanner sc = new Scanner(s);
        sc.useDelimiter(", ");
        if (sc.hasNext())
            return sc.next();
        return null;
    }
    private static FileInputStream createFileInputStream() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(SIGNATURES_PATH);
        } catch (FileNotFoundException ex) {
            System.err.println("Failed to open \"signatures.txt\"");
            System.err.println(ex);
        }
        return inputStream;
    }
    private static void closeStream(FileInputStream in) {
        try {
            if (in != null)
                in.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}