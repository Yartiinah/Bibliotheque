@echo off
REM Script pour déployer le .war dans le dossier webapps de Tomcat

REM Modifier ce chemin si besoin
set TOMCAT_WEBAPPS=C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps
set WAR_FILE=target\spring-web-1.0-SNAPSHOT.war

if not exist "%WAR_FILE%" (
    echo Le fichier %WAR_FILE% n'existe pas !
    exit /b 1
)

if not exist "%TOMCAT_WEBAPPS%" (
    echo Le dossier Tomcat webapps n'existe pas !
    exit /b 1
)

echo Copie du fichier %WAR_FILE% vers %TOMCAT_WEBAPPS% ...
copy /Y "%WAR_FILE%" "%TOMCAT_WEBAPPS%"

echo Deploiement termine. Redemarre Tomcat si necessaire.
