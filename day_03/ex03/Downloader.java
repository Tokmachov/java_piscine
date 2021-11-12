
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public class Downloader implements Runnable {
    static int ids = 0;
    int id;
    public Downloader() {
        this.id = ids;
        ids++;
    }

    @Override
    public void run() {
        Map.Entry<Integer, String> numAndUrl;
        while ((numAndUrl = Program.getUrl()) != null) {
            try {
                downLoad(numAndUrl);
            } catch (IOException e) {
                System.err.println("Exception occurred when downloading file: " + e);
            }
        }
    }

    private void downLoad(Map.Entry<Integer, String> numAndUrl) throws IOException {
        String fileUrl = numAndUrl.getValue();
        Integer fileNum = numAndUrl.getKey();

        URL url = new URL(fileUrl);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "NING/1.0");
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));

        File file = new File("downloads/" + fileName);

        try (BufferedInputStream fis = new BufferedInputStream(conn.getInputStream());
             FileOutputStream fos = new FileOutputStream(file)) {
            System.out.println("Thread " + id + " started to download file number " + fileNum);
            Files.copy(fis, Paths.get("downloads/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
