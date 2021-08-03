import java.util.Random;
import java.util.*;
import java.util.stream.*;

public class RandomIntsArrayGenerator {
    public static int[] generate(int bottomElemetValue, int topElementValue, int count) {
        Random random = new Random();
        IntStream is = random.ints(bottomElemetValue, topElementValue + 1);
        PrimitiveIterator.OfInt iter = is.iterator();        
        int[] arr = new int[count];
        
        for (int i = 0; i < arr.length; i++) {
            arr[i] = iter.next();
        }
        return arr;
    }
}