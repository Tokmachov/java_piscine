package edu.school21.printer.logic;

public class Printer {
    public static void print(int[][] img, char black, char white) {
        for (int h = 0; h < img.length; h++)
        {
            for (int w = 0; w < img[0].length; w++) {
                if (img[h][w] == 1)
                    System.out.print(black);
                else 
                    System.out.print(white);
            }
            System.out.println();
        }
    }
}