import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.IllegalArgumentException;
import java.nio.file.InvalidPathException;
import java.nio.file.Files;
import java.util.Scanner;

public class FileManager {
    private Path currentDir;
    public FileManager(String currentDir) throws InvalidPathException, IllegalArgumentException {
        Path path = Paths.get(currentDir);
        if (Files.exists(path) == false) {
            throw new IllegalArgumentException("Error: path provided does not exist. Pleas provide existing path");
        }
        if (path.isAbsolute() == false) {
            throw new IllegalArgumentException("Error: please provide absolute path to working directory");
        }
        if (Files.isDirectory(path) == false) {
            throw new IllegalArgumentException("Error: path provided does not represent a directory. Please provide directory path");
        }
        this.currentDir = path;
        System.out.println(currentDir);
    }
    public void run() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String cmd = sc.nextLine();
                if (cmd.equals("ls")) {
                    Command.ls(currentDir);
                } else if (Command.isMvCommand(cmd)) {
                    Command.mv(Command.getMvSourceArg(cmd), Command.getMvTargetArg(cmd), currentDir);
                } else if (Command.isCd(cmd)) {
                    try {
                        Path newDir = Command.getCdDestPath(cmd);
                        currentDir = Command.cd(currentDir, newDir);
                        System.out.println(currentDir);
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                } else if (cmd.equals("exit")) {
                    System.exit(0);
                } else {
                    System.err.println("Error: unknown command");
                }
            }
        }
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: please provide absolute path to working folder as the only argument");
            System.exit(-1);
        }
        try {
            String path = parseCurrentDirPath(args[0]);
            if (path == null)
                throw new IllegalArgumentException("Error: wrong path format. Please provide path of type \"--current-folder=PATH\"");
            System.out.println(path);
            FileManager fm = new FileManager(path);
            fm.run();
        } catch (InvalidPathException ex) {
            System.err.println(ex);
            System.exit(-1);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex);
            System.exit(-1);
        } 
    }
    private static String parseCurrentDirPath(String sourceOfPath) {
        if (sourceOfPath.indexOf("--current-folder=") != 0) {
            return null;
        }
        return sourceOfPath.substring(sourceOfPath.indexOf('=') + 1);
    }
}