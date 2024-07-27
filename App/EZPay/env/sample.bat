@echo off

set path=%~dp0

@echo Starting to configure database ... 
cd "%path%\..\data\transaction\bin\" 
REM mysqladmin -u root shutdown
mysqld --install
mysqld --install MySQL --defaults-file=%path%\..\data\transaction\my.ini
pause