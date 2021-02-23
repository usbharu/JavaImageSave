@echo off

If Exist jre ( jre\bin\java.exe -jar JavaSaveImage.jar %* ) Else ( java -jar JavaSaveImage.jar %* )
