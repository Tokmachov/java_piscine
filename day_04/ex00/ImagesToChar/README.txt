mkdir target
find ./src/java -type f -name "*.java" > project_files.txt
javac -cp target -sourcepath src/java -d target @project_files.txt
java -cp target edu.school21.printer.app.ConsoleImagePrinter . 0 it.bmp
