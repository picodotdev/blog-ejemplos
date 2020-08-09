#!/usr/bin/env bash
docker run --rm -p 80:80 -p 443:443 \
  -v `pwd`/httpd.conf:/usr/local/apache2/conf/httpd.conf:ro \
  -v `pwd`/httpd-vhosts.conf:/usr/local/apache2/conf/extra/httpd-vhosts.conf:ro \
  -v `pwd`/localhost.crt:/usr/local/apache2/conf/extra/localhost.crt:ro \
  -v `pwd`/localhost.key:/usr/local/apache2/conf/extra/localhost.key:ro \
  -v `pwd`/.localhost.htpasswd:/usr/local/apache2/conf/extra/.localhost.htpasswd:ro \
  httpd:alpine
