apachectl -f ~/httpd-root/httpd-hell.conf
java -classpath /home/studs/s467570/httpd-root/fcgi-bin/fastcgi-lib.jar -DFCGI_PORT=24571 -jar /home/studs/s467570/httpd-root/fcgi-bin/fastcgi-server.jar
