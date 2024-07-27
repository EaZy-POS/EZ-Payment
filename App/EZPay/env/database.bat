@echo off
IF NOT EXIST  "C:\EZSystem" (
	mkdir "C:\EZSystem"
}

cls
xcopy /s /i "%CD%\..\data" "C:\EZSystem"

cls 
cd "C:\EZSystem\data\transaction\bin\" 
mysqladmin -u root shutdown
mysqld --install
mysqld --install MySQL --defaults-file=C:\EZSystem\data\transaction\my.ini
 
IF EXIST  "C:\EZSystem\data\transaction\data" (
	echo Configurating. Waiting ... 
	cd "C:\EZSystem\data\transaction\bin\" 
	MySQLInstanceConfig.exe -i -q ServiceName=MySQL5.5 RootPassword=toor ServerType=DEVELOPER DatabaseType=INODB Port=3390 Charset=utf8
	echo MySQL Instance Configured. Service started
	echo Done. 
	cd "C:\EZSystem\data\transaction\bin\"
	mysqld skip-grant-tables
	echo done
)
echo done

NET START MYSQL5.5