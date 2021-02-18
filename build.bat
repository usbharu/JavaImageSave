@echo off

javac -encoding UTF-8 -d bin -classpath ./lang;./src -sourcepath src\ src\harujisaku\javasaveimage\JavaSaveImage.java

if not %ERRORLEVEL% == 0 (
pause
exit
)

jar -cvf JavaSaveImage.jar -C bin\ .
jar uvfm JavaSaveImage.jar manifest.mani

java -classpath lang -jar JavaSaveImage.jar
if not %ERRORLEVEL% == 0 (
pause
exit
)
java -classpath .;lang -jar JavaSaveImage.jar -t 0 -l 1 -r 90
pause
del *.jpg
del *.png
del *.jpeg
