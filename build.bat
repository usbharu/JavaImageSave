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
java -classpath .;lang -jar JavaSaveImage.jar -t "t" -l 1
pause
del *.jpg
del *.png
del *.jpeg
