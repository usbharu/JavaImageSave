@echo off

javac -encoding UTF-8 -d bin -classpath ./lang;./src -sourcepath src\ src\harujisaku\javasaveimage\JavaSaveImage.java

jar -cvf JavaSaveImage.jar -C bin\ .
jar uvfm JavaSaveImage.jar manifest.mani

java -classpath lang -jar JavaSaveImage.jar
java -classpath .;lang -jar JavaSaveImage.jar -t "t" -l 1
del *.jpg
del *.png
del *.jpeg
pause
