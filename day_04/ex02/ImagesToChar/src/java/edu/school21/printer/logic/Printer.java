package edu.school21.printer.logic;
import com.diogonunes.jcolor.*;

import java.util.HashMap;
import java.util.Map;

public class Printer {
    private static Map<String, Attribute> colorsForColorNames;
    static {
        colorsForColorNames = new HashMap<>();
        colorsForColorNames.put("RED", Attribute.RED_BACK());
        colorsForColorNames.put("WHITE", Attribute.WHITE_BACK());
    }
    public static void print(int[][] pixelMap, String whitePxlColor, String blackPixelColor) {
        for (int h = 0; h < pixelMap.length; h++)
        {
            for (int w = 0; w < pixelMap[h].length; w++) {
                if (pixelMap[h][w] == 1) {
                    Attribute bkgColor = Attribute.BLUE_BACK();
                    System.out.print(Ansi.colorize(" ", bkgColor));
                } else {
                    Attribute bkgColor = Attribute.BRIGHT_CYAN_BACK();
                    System.out.print(Ansi.colorize(" ", bkgColor));
                }
            }
            System.out.println();
        }
    }
}