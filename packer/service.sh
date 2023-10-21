
sudo apt-get update

#cd ..

sudo apt install openjdk-17-jdk -y

sleep 30

sudo apt install mariadb-server -y

sleep 30

sudo apt install maven -y

sleep 30

sudo systemctl start mariadb

#pwd=`sudo grep 'temporary password' /var/log/mysqld.log | rev | cut -d':' -f 1 | rev | xargs`


sudo mariadb -uroot -e "ALTER USER 'root'@'localhost' IDENTIFIED BY 'Qq18284530122';"


mariadb -uroot -pQq18284530122 -e "CREATE DATABASE IF NOT EXISTS demo"


sudo mvn -B package --file webapp/pom.xml

#cd /root/webapp
#
#mvn spring-boot:run