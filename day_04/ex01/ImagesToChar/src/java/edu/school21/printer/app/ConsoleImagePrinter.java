package edu.school21.printer.app;
import edu.school21.printer.logic.*;

import java.io.*;
import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;

public class ConsoleImagePrinter {
    public static void main(String[] av) {
        if (ArgumentsValidator.areArgumentsValid(av) == false)
        {
            printWrongArgumentsErrorMessage();
            System.exit(-1);
        }
        Character black = ArgumentsParser.getBlackPixelChar(av);
        Character white = ArgumentsParser.getWhitePixelChar(av);
        File file;
        // try {
        //     file = new File("../../../../");
        // } catch (NullPointerException ex) {
        //     System.err.println(ex);
        //     return null;
        // }
        //  = ArgumentsParser.getFile(av);
        // if (black == null || white == null || file == null)
        // {
        //     System.err.println("Error: failed to parse arguments error");
        //     System.exit(-1);
        // }
        // int[][] image = BmpToCharImageConverter.convert(file);
        // Printer.print(image, black.charValue(), white.charValue());
    }
    private static void printWrongArgumentsErrorMessage() {
        System.err.println("Error: wrong number or type of arguments to program");
        System.err.println("Program requires 2 arguments:");
        System.err.println("1 - char");
        System.err.println("2 - char");
    }
    
}