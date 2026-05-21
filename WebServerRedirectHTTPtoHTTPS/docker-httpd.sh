#!/usr/bin/env bash
docker run --rm --name apache -p 80:80 -p 443:443 -v `pwd`/httpd.conf:/usr/local/apache2/conf/httpd.conf:ro -v `pwd`/localhost.key:/usr/local/apache2/conf/localhost.key:ro -v `pwd`/localhost.crt:/usr/local/apache2/conf/localhost.crt:ro httpd
