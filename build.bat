@echo off

javac -encoding UTF-8 -d bin -classpath ./lang;./src -sourcepath src\ src\harujisaku\javasaveimage\JavaSaveImage.java

if not %ERRORLEVEL% == 0 (
pause
exit
)

jar -cvf JavaSaveImage.jar -C bin\ .
jar uvfm JavaSaveImage.jar MANIFEST.MF

java -jar JavaSaveImage.jar -h
if not %ERRORLEVEL% == 0 (
pause
exit
)
java -jar JavaSaveImage.jar "C:\\Users\\haruj\\Documents\\GitHub\\JavaImageSave\\option.txt"
if not %ERRORLEVEL% == 0 (
pause
exit
)
java -jar JavaSaveImage.jar --dev -t "java" -l 1
pause
del *.jpg
del *.png
del *.jpeg
exit
