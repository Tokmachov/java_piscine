
mkdir -p target
cp -r src/resources target
find src/java -type f -name "*.java" > project_files_list.txt
javac -cp target -sourcepath src/java -d target @project_files_list.txt
jar cfmv target/images-to-chars-printer.jar src/manifest.txt -C target .
java -jar target/images-to-chars-printer.jar . 0
#rm -rf target
#rm project_files_list.txt