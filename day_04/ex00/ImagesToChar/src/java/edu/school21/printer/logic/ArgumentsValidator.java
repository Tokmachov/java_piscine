package edu.school21.printer.logic;

import java.io.*;

public class ArgumentsValidator {
    public static boolean areArgumentsValid(String[] av) {
        if (av.length != 3)
            return false;
        if (av[0].length() != 1)
            return false;
        if (av[1].length() != 1)
            return false;
        File f = new File(av[2]);
        if (f.exists() == false)
            return false;
        return true;
    }
}