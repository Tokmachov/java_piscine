import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Integer;

class FileTypeSignatureReader {
    private final static int NUMBER_OF_BYTES_IN_SYGNATURE = 8;
    
    public static LinkedList<Integer> readFileTypeSygnature(String path) {
        FileInputStream fis = createFileInputStream(path);
        if (fis == null)
            return null;
        LinkedList<Integer> bytesList = new LinkedList<Integer>();
        int numberOfBytesToRead = NUMBER_OF_BYTES_IN_SYGNATURE;
        try {
            while ((fis.available() >= numberOfBytesToRead) && numberOfBytesToRead > 0) {
                int b = fis.read();
                if (b == -1)
                {
                    closeFileInputStream(fis);
                    return null;
                }
                bytesList.add(new Integer(b));
                numberOfBytesToRead--;
            }
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
        if (bytesList.size() != NUMBER_OF_BYTES_IN_SYGNATURE)
            return null;
        return bytesList;
    }
    private static FileInputStream createFileInputStream(String path) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        return fis;
    }
    private static void closeFileInputStream(FileInputStream fis) {
        try {
            fis.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}