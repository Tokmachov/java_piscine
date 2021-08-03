mkdir -p target/resources
javac -d target -cp target -sourcepath src/java src/java/edu/school21/printer/app/ConsoleImagePrinter.java
cp src/java/resources/it.bmp target/resources
jar cfm target/images-to-chars-printer.jar src/Manifest.txt -C target/*