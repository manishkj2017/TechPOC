set CATALINA_HOME=C:\manish\Development\apache-tomcat-8.0.44

start C:\manish\Development\apache-tomcat-8.0.44\bin\shutdown.bat

rmdir /s /q C:\manish\Development\apache-tomcat-8.0.44\webapps\ShopGUI

copy /y C:\manish\Development\staging\ShopGUI.war C:\manish\Development\apache-tomcat-8.0.44\webapps\ShopGUI.war

echo "starting shop server"
start "ShopServer" java -Xms256m -Xmx1g -jar C:\manish\Development\staging\ShopServer\ShopServer-1.0-onejar.jar > server.log
echo "sleeping for 10 seconds"

timeout /t 10 /nobreak

echo "starting shop client"
start "ShopClient" java -Xms256m -Xmx1g -jar ShopClient\ShopClient-1.0-onejar.jar > client.log

wmic PROCESS where  name='java.exe" 

echo "starting shop gui"
start C:\manish\Development\apache-tomcat-8.0.44\bin\startup.bat





