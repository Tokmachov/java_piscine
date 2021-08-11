import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class FileAnalyzer {
    private static String UNDEFINED_IDENTIFICATION_RESULT = "UNDEFINED";
    private static String PROCESSED_IDENTIFICATION_RESULT = "PROCESSED";
    public static void main(String[] av) {
        File knownFileTypesDescriptions = new File("signatures.txt");
        if (!knownFileTypesDescriptions.isFile()) {
            System.err.println("Error: wrong signatures.txt path");
            System.exit(-1);
        }
        List<FileTypeDescription> fileTypesDescriptions = null;
        try {
            fileTypesDescriptions = parseFileTypesDescriptions(knownFileTypesDescriptions);
        } catch (Exception ex) {
            System.err.println("Error: failed to parse file signatures.");
            System.exit(-1);
        }
        Scanner sc = new Scanner(System.in);
        File resultFile = new File("result.txt");
        try {
            resultFile.createNewFile();
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(-1);
        }
        while (true) {
            String pathToFileForIdentification = sc.nextLine();
            if (pathToFileForIdentification.equals("42")) System.exit(0);
            File f = new File(pathToFileForIdentification);
            if (!f.isFile()) { System.err.println("Error: wrong path"); continue; }
            String fileTypeName = identifyFileType(fileTypesDescriptions, f);
            if (fileTypeName.equals(UNDEFINED_IDENTIFICATION_RESULT)) {
                System.out.println(UNDEFINED_IDENTIFICATION_RESULT);
            } else {
                System.out.println(PROCESSED_IDENTIFICATION_RESULT);
                writeToFile(fileTypeName, resultFile);
            }
        }
    }
    private static List<FileTypeDescription> parseFileTypesDescriptions(File f) throws FileNotFoundException, IOException, DataFormatException {
        List<FileTypeDescription> lst = new ArrayList<>();
        try (BufferedReader bufReader = new BufferedReader(new FileReader(f))) {
            String line = null;
            while ((line = bufReader.readLine()) != null) {
                String[] components = line.split("\\s+");
                String fileTypeName = getFileTypeName(components);
                List<Short> signature = getSignature(components);
                if (fileTypeName == null || fileTypeName == null) {
                    throw new DataFormatException();
                }
                FileTypeDescription ft = new FileTypeDescription();
                ft.name = fileTypeName;
                ft.signature = signature;
                lst.add(ft);
            }
        }
        return lst;
    }
    private static String getFileTypeName(String[] arr) {
        if (arr.length < 2) return null;
        String fileTypeName = arr[0];
        if (!fileTypeName.endsWith(",")) return null;
        return fileTypeName.substring(0, fileTypeName.length() - 1);
    }

    private static List<Short> getSignature(String[] arr) {
        if (arr.length < 2) return null;
        List<Short> lst = new ArrayList<>();
        for (int idx = 0; idx < arr.length; idx++) {
            if (idx == 0) continue;
            Short s = null;
            try {
                s = Short.parseShort(arr[idx], 16);
            } catch (NumberFormatException ex) {}
            if (s == null) return null;
            lst.add(s);
        }
        return lst;
    }
    private static String identifyFileType(List<FileTypeDescription> descriptions, File f) {
        Short[] fileSignature = getTestFileSignature(f);
        for (FileTypeDescription desc : descriptions) {
            Short[] knownSignature = (desc.signature.toArray(new Short[0]));
            if (areSignaturesEqual(knownSignature, fileSignature)) return desc.name;
        }
        return "UNDEFINED";
    }
    private static Short[] getTestFileSignature(File f) {
        Short[] sign = new Short[10];
        try (FileInputStream fis = new FileInputStream(f)) {
            for (int i = 0; i < 10; i++) {
                Integer b = fis.read();
                sign[i] = b.shortValue();
            }
        } catch (Exception e) {
            return null;
        }
        return sign;
    }
    private static boolean areSignaturesEqual(Short[] knownSignature, Short[] testFileSignature) {
        if (testFileSignature.length < knownSignature.length)
            return false;
        for (int i = 0; i < knownSignature.length; i++) {
            if (knownSignature[i].equals(testFileSignature[i]) == false) {
                return false;
            }
        }
        return true;
    }

    private static void writeToFile(String s, File f) {
        try (BufferedWriter bos = new BufferedWriter(new FileWriter(f, true))) {
            bos.write(s + "\n");
        } catch (Exception ex) {
            System.err.println("Error: failed to write file identification result to file");
        }
    }
}
