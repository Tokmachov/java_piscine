package edu.school21.printer.app;
import edu.school21.printer.logic.*;
import java.io.*;

public class ConsoleImagePrinter {
    public static void main(String[] av) {
        if (ArgumentsValidator.areArgumentsValid(av) == false)
        {
            printWrongArgumentsErrorMessage();
            System.exit(-1);
        }
        Character black = ArgumentsParser.getBlackPixelChar(av);
        Character white = ArgumentsParser.getWhitePixelChar(av);
        if (black == null || white == null) {
            printWrongArgumentsErrorMessage();
            System.exit(-1);
        }
        try (InputStream is = ConsoleImagePrinter.class.getResourceAsStream("/resources/it.bmp")) {
            int[][] imagePixelMap = BmpToCharImageConverter.convert(is);
            Printer.print(imagePixelMap, black.charValue(), white.charValue());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    private static void printWrongArgumentsErrorMessage() {
        System.err.println("Error: wrong number or type of arguments to program");
        System.err.println("Program requires 2 arguments:");
        System.err.println("1 - char");
        System.err.println("2 - char");
    }
    
}