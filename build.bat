@echo off

javac -encoding UTF-8 -d bin -sourcepath src\ src\harujisaku\javasaveimage\JavaSaveImage.java

jar -cvf JavaSaveImage.jar -C bin\ .
jar uvfm JavaSaveImage.jar manifest.mani

java -jar JavaSaveImage.jar
java -jar JavaSaveImage.jar -t "t" -l 1
del *.jpg
del *.png
del *.jpeg
pause
