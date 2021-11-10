import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NotDirectoryException;
import java.nio.file.NoSuchFileException;
import java.util.stream.Stream;

public class Command {
	public static void mv(Path what, Path where, Path workingDir) {
		try {
			what = workingDir.resolve(what);
			where = workingDir.resolve(where);
			if (Files.isDirectory(where)) {
				where = where.resolve(what.getFileName());
			}
			Path resultPath = Files.move(what, where);
		} catch (NoSuchFileException ex) {
			System.err.println("Error: target directory does not exist");
		} catch (FileAlreadyExistsException ex) {
		 	System.err.println("Error: attemt to overwrite existing file or directory. Please specify non existing name of file");
		} catch (IOException ex) {
		 	System.err.println("Error: source file doesn't exist.");
		}
	}
	
	public static void ls(Path currentDir) {
		try (Stream<Path> currentDirContents = Files.list(currentDir)) {
			currentDirContents.forEach((Path internalDir) -> {
				try {
					long fileSizeInKb = Files.size(internalDir) / 1024;
					System.out.println(internalDir.getFileName() + " " + fileSizeInKb + " KB");
				} catch (Exception ex) {
					System.out.println(internalDir.getFileName() + " " + "_ KB");
				}
			});
		} catch (NotDirectoryException  ex) {
			System.err.println("Error: current directory path does not represent directory");
		} catch (IOException ex) {
			System.err.println("Error: failed to open directory");
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
	public static boolean isMvCommand(String command) {
		String[] splited = command.split("\\s+");
		if (!splited[0].equals("mv")) {
			return false;
		}
		if (splited.length != 3) {
			System.err.println("Error: you probably provided wrong arg quantity to mv command. mv requires 2 arguments: source path and targer path");
			return false;
		}
		return true;
	}
	public static Path getMvSourceArg(String command) {
		return Paths.get(command.split("\\s+")[1]);
	}
	public static Path getMvTargetArg(String command) {
		return Paths.get(command.split("\\s+")[2]);
	}
	public static boolean isCd(String command) {
		String[] splited = command.split("\\s+");
		if (!splited[0].equals("cd")) {
			return false;
		}
		if (splited.length != 2) {
			System.err.println("Error: you probably provided wrong args quantity to cd command. cd requires 1 argument: dest path");
			return false;
		}
		return true;
	}
	public static Path getCdDestPath(String command) {
		String[] splited = command.split("\\s+");
		return Paths.get(splited[1]);
	}
	public static Path cd(Path currentDir, Path newDir) {
		Path resultCurrentDir = currentDir.resolve(newDir);
		if (Files.exists(resultCurrentDir) == false) {
            throw new IllegalArgumentException("Error: path provided does not exist. Pleas provide existing path");
        }
        if (Files.isDirectory(resultCurrentDir) == false) {
            throw new IllegalArgumentException("Error: path provided does not represent a directory. Please provide directory path");
        }
		return resultCurrentDir.normalize();
	}
}