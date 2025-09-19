cp fastcgi-server/target/fastcgi-server-1.0-SNAPSHOT-jar-with-dependencies.jar httpd-root/fcgi-bin/fastcgi-server.jar
scp -r httpd-root helios:~
scp -r static helios:~/httpd-root