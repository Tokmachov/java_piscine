mkdir target
javac -cp target -sourcepath src/java -d target src/java/edu/school21/printer/app/ConsoleImagePrinter.java
cp it.bmp target/edu/school21/printer/app
java -cp 'target' edu.school21.printer.app.ConsoleImagePrinter . 0 it.bmp
