import java.util.LinkedList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Program {
    public static void main(String[] av) {
        LinkedList<FileTypeTraits> fileTypesTraitsList = null;
        LinkedList<Integer> fileSignature = null;
        
        fileTypesTraitsList = FilesTraitesReader.readFileTypeTraits();
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = createPrintWriter("result.txt");
        if (pw == null || fileTypesTraitsList == null || sc == null)
            System.exit(-1);
        while (sc.hasNextLine()) {
            String filePath = sc.nextLine();
            if (filePath.equals("42"))
            {
                pw.close();
                System.exit(0);
            }
            fileSignature = FileTypeSignatureReader.readFileTypeSygnature(filePath);
            if (fileSignature == null)
            {
                System.err.println("Failed to read file signature.");
                continue ;
            }
            else
            {
                String fileType = identifyFileType(fileSignature, fileTypesTraitsList);
                if (fileType != null)
                {
                    pw.println(fileType);             
                    System.out.println("PROCESSED");
                }
                else
                    System.out.println("UNDEFINED");
            }
        }
    }
    private static String identifyFileType(LinkedList<Integer> fileSignature, LinkedList<FileTypeTraits> fileTypesTraitsList) {
        for (FileTypeTraits el : fileTypesTraitsList)
        {
            if (areSignaturesEqual(fileSignature, el.signature)) {
                return el.type;
            }
        }
        return null;
    }
    private static boolean areSignaturesEqual(LinkedList<Integer> analysedFileSign, LinkedList<Integer> signToCompare) {
        if (signToCompare.size() > analysedFileSign.size())
            return false;
        for (int i = 0; i < signToCompare.size(); i++) {
            if (analysedFileSign.get(i).equals(signToCompare.get(i)) == false)
                return false;
        }
        return true;
    }
    private static PrintWriter createPrintWriter(String filePath) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(filePath);
        }
        catch (FileNotFoundException ex)
        {
            System.err.println(ex);
        }
        return pw;
    }
}