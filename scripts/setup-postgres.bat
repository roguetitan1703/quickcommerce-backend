@echo off
echo Snap Quick Commerce PostgreSQL Setup Script
echo ===========================================
echo.

REM Check if PostgreSQL is installed
WHERE psql > nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo PostgreSQL is not installed or not in PATH.
    echo Please download and install PostgreSQL from https://www.postgresql.org/download/windows/
    echo.
    echo After installation, make sure to add the PostgreSQL bin directory to your PATH:
    echo e.g., C:\Program Files\PostgreSQL\15\bin
    echo.
    pause
    exit /b 1
)

echo Found PostgreSQL installation.
echo.
echo This script will create the required database for Snap Quick Commerce.
echo.

set /p PGUSER=Enter PostgreSQL username (default: postgres): 
if "%PGUSER%"=="" set PGUSER=postgres

set /p PGPASSWORD=Enter PostgreSQL password: 
if "%PGPASSWORD%"=="" (
    echo Password cannot be empty.
    pause
    exit /b 1
)

echo.
echo Creating database 'snapquickcommerce'...

REM Create the database
psql -U %PGUSER% -c "CREATE DATABASE snapquickcommerce;" postgres

IF %ERRORLEVEL% NEQ 0 (
    echo Failed to create database. Please check your credentials.
    pause
    exit /b 1
)

echo.
echo Database created successfully!
echo.
echo You can now run the application with the 'prod' profile:
echo mvn spring-boot:run -Dspring-boot.run.profiles=prod
echo.
echo The application will automatically create the schema and load sample data.
echo.
pause 