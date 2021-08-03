import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.lang.Integer;
import java.util.LinkedList;
import java.util.*;
import java.lang.Math;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Program {
    public static void main(String[] av) {
        if (av.length != 2)
        {
            System.err.println("Error: app needs arguments to start");
            System.exit(-1);
        }
        String path1 = av[0];
        String path2 = av[1];
        FileWordByWordScanner fs1 = null;
        FileWordByWordScanner fs2 = null;
        
        try {
            fs1 = new FileWordByWordScanner(path1);
            fs2 = new FileWordByWordScanner(path2);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            System.exit(-1);
        }
        TreeMap<String, Integer> dict1 = new TreeMap<String, Integer>();
        TreeMap<String, Integer> dict2;
        LinkedList<String> wordsOfFile1 = new LinkedList<String>();
        LinkedList<String> wordsOfFile2 = new LinkedList<String>();
        copyWordsFromFileToDictionaryAndList(dict1, wordsOfFile1, fs1);
        copyWordsFromFileToDictionaryAndList(dict1, wordsOfFile2, fs2);
        fs1.close();
        fs2.close();
        writeDictionaryToFile(dict1);
        dict2 = new TreeMap<String, Integer>(dict1);
        countWords(wordsOfFile1, dict1);
        countWords(wordsOfFile2, dict2);
        double similarity = calculateSimilarity(dict1.values(), dict2.values());
        System.out.printf("Similarity = %.2f\n", similarity);
    }
    
    private static void copyWordsFromFileToDictionaryAndList(TreeMap<String, Integer> m, LinkedList<String> wordsOfFile, FileWordByWordScanner fileSrc) {
        while (fileSrc.hasNextWord()) {
            String word = fileSrc.nextWord();
            m.put(word, new Integer(0));
            wordsOfFile.add(word);
        }
    }
    
    private static void countWords(LinkedList<String> words, TreeMap<String, Integer> m) {
        for (String word : words) {
            Integer oldValue = m.get(word);
            m.put(word, new Integer(oldValue.intValue() + 1));
        }
    }
    
    private static double calculateSimilarity(Collection<Integer> wordsFrequencies1, Collection<Integer> wordsFrequencies2) {
        Iterator<Integer> it1 = wordsFrequencies1.iterator();
        Iterator<Integer> it2 = wordsFrequencies2.iterator();
        int numerator = 0;
        double denominator = 0;
        int sumOfSquares1 = 0;
        int sumOfSquares2 = 0;
        double sqrt1;
        double sqrt2;
        while (it1.hasNext() && it2.hasNext()) {
            int freq1 = it1.next().intValue();
            int freq2 = it2.next().intValue();
            numerator += freq1 * freq2;
            sumOfSquares1 += freq1 * freq1;
            sumOfSquares2 += freq2 * freq2;
        }
        sqrt1 = Math.sqrt((double)sumOfSquares1);
        sqrt2 = Math.sqrt((double)sumOfSquares2);
        denominator = sqrt1 * sqrt2;
        return (double)numerator/denominator;
    }
    private static void writeDictionaryToFile(TreeMap<String, Integer> m) {
        try {
            FileWriter fw = new FileWriter("dictionary.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (String word : m.keySet()) {
                bw.write(word, 0, word.length());
                bw.write('\n');
            }
            bw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}