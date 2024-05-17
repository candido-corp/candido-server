@echo off
setlocal enabledelayedexpansion

cd /d ./../

rem Legge il file .env e imposta le variabili di ambiente
for /f "tokens=1,2 delims==" %%a in (.env) do (
    set %%a=%%b
)

rem Naviga nella directory del progetto
cd /d ./candido-server/

rem Esegue il build di Gradle
call gradlew clean build

endlocal
