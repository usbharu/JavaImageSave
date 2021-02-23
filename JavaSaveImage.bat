@echo off

If Exist jre ( jre\bin\java.exe -classpath .;lang -jar JavaSaveImage.jar %* ) Else ( java -classpath .;lang -jar JavaSaveImage.jar %* )
