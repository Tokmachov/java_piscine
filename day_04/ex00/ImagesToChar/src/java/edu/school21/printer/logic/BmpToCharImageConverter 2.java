package edu.school21.printer.logic;

import java.io.*;
import javax.imageio.stream.FileImageInputStream;
import java.lang.SecurityException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.nio.ByteOrder;
 
 public class BmpToCharImageConverter {
    public static int[][] convert(File imagePath) {
        try (FileImageInputStream f = new FileImageInputStream(imagePath)) {
            f.setByteOrder(ByteOrder.LITTLE_ENDIAN);
            long bpp = getIntValueFromBmpFileAtOffset(f, 28);
            if (bpp != 1)
            {
                System.err.println("Error: image is not black and white");
                return null;
            }
            int imgOffset = getIntValueFromBmpFileAtOffset(f, 10);;
            int imgWidth = getIntValueFromBmpFileAtOffset(f, 18);
            int imgHeight = getIntValueFromBmpFileAtOffset(f, 22);
            f.seek(imgOffset);
            int[][] img = new int[imgHeight][imgWidth];
            for (int h = imgHeight - 1; h >= 0; h--) {
                for (int w = 0; w < imgWidth; w++) {
                    int bit = f.readBit();
                    img[h][w] = bit;
                }
                f.skipBytes(2);
            }
            return img;
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    private static int getIntValueFromBmpFileAtOffset(FileImageInputStream f, int offset) {
        try {
            f.seek(offset);
            return (int)f.readUnsignedInt();
        } catch (IOException ex) {
            System.err.println(ex);
            return -1;
        }
    }
 }