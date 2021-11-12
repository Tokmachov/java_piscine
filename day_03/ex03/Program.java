
import java.io.*;
import java.util.*;

public class Program {
    private static final String URLS_SOURCE_PATH = "files_urls.txt";

    private static Iterator<Map.Entry<Integer, String>> numsAndFileUrlsIterator;

    public static void main(String[] args) {
        Integer threadsCount = null;
        try {
            threadsCount = parseThreadCount(args);
            if (threadsCount <= 0) {
                throw new IllegalArgumentException("Threads count must be positive");
            }
        } catch (Exception e) {
            System.err.println("Failed to parse number of threads because of: " + e);
            System.exit(-1);
        }
        try {
            Map<Integer, String> numsAndFileUrls = extractFileLinks();
            numsAndFileUrlsIterator = numsAndFileUrls.entrySet().iterator();
        } catch (Exception e) {
            System.err.println("Failed to extract links from file: " + e);
            System.exit(-1);
        }
        File theDir = new File("downloads");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(new Downloader());
            thread.start();
        }
    }

    private static int parseThreadCount(String[] args) {
        if (args.length == 1) {
            String[] components = args[0].split("=");
            if (components.length == 2 && components[0].equals("--threadsCount")) {
                return Integer.parseInt(components[1]);
            }
        }
        throw new IllegalArgumentException("Unexpected number of arguments. There must be one argument of kind: \"--threadsCount=3\"");
    }

    private static Map<Integer, String> extractFileLinks() throws IOException {
        Map<Integer, String> numsAndLinks = new LinkedHashMap<>();
        try (FileInputStream fis = new FileInputStream(URLS_SOURCE_PATH)) {
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] components = line.split("\\s");
                if (components.length != 2) {
                    throw new IllegalArgumentException("Unexpected format of file with links");
                }
                numsAndLinks.put(Integer.parseInt(components[0]), normalizeUrl(components[1]));
            }
        }
        return numsAndLinks;
    }
    
    private static String normalizeUrl(String url) {
        if (!url.contains("https")) {
            if (url.contains("http")) {
                return url.replace("http", "https");
            }
        }
        return url;
    }

    synchronized public static Map.Entry<Integer, String> getUrl() {
        if (numsAndFileUrlsIterator.hasNext()) {
            return numsAndFileUrlsIterator.next();
        }
        return null;
    }
}