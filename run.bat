@echo off
echo ===============================
echo Compiling Java Program...
echo ===============================

REM COMPILE ALL .java FILES
javac -cp ".;lib/mysql-connector-j-9.5.0.jar" -d . Main.java database\Database.java database\DBUtil.java gui\*.java models\*.java users\*.java

IF %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ COMPILATION FAILED!
    pause
    exit /b
)

echo.
echo ===============================
echo Running Program...
echo ===============================

java -cp ".;lib/mysql-connector-j-9.5.0.jar" Main

echo.
pause
