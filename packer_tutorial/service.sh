sudo apt-get update

cd ..

sudo apt install openjdk-17-jdk

sleep 30,

sudo apt install mariadb-server

sleep 30,

sudo apt install maven

sleep 20,

sudo systemctl start mysql

pwd=`sudo grep 'temporary password' /var/log/mysqld.log | rev | cut -d':' -f 1 | rev | xargs`


mysql -uroot -p$pwd --connect-expired-password -e "Alter user 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Qq18284530122'"


mysql -uroot -pRobb-123 -e "CREATE DATABASE IF NOT EXISTS demo_db"

cd /root/webapp

mvn spring-boot:run