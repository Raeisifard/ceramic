echo off
cd /D "%~dp0"
echo Current working directory: %cd%
mvn install:install-file -Dfile=.\lib\vertx-sse-4.0.0.jar -DgroupId=io.vertx -DartifactId=vertx-sse -Dversion=4.0.0 -Dpackaging=jar
