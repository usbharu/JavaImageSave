@echo off

javac -encoding UTF-8 -d bin -classpath ./lang;./src -sourcepath src\ src\harujisaku\javasaveimage\JavaSaveImage.java

if not %ERRORLEVEL% == 0 (
pause
exit
)

jar -cvf JavaSaveImage.jar -C bin\ .
jar uvfm JavaSaveImage.jar manifest.mani

java -jar JavaSaveImage.jar -h
if not %ERRORLEVEL% == 0 (
pause
exit
)
java -jar JavaSaveImage.jar -t "java" -l 1 -r 90 -dev
pause
del *.jpg
del *.png
del *.jpeg
exit
