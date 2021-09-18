package edu.school21.printer.logic;

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;
import java.io.File;

public class ArgumentsParser {
    public static Character getBlackPixelChar(String[] av) throws IndexOutOfBoundsException {
        try {
            return new Character(av[0].charAt(0));
        } catch (IndexOutOfBoundsException ex) {
            System.err.println(ex);
            return null;
        }
    }
    public static Character getWhitePixelChar(String[] av) throws IndexOutOfBoundsException {
        try {
            return new Character(av[1].charAt(0));
        } catch (IndexOutOfBoundsException ex) {
            System.err.println(ex);
            return null;
        }
    }
}