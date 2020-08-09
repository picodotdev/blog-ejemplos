#!/usr/bin/env bash
docker run --rm -p 80:80 \
  -v `pwd`/httpd.conf:/usr/local/apache2/conf/httpd.conf:ro \
  -v `pwd`/httpd-vhosts.conf:/usr/local/apache2/conf/extra/httpd-vhosts.conf:ro \
  httpd:alpine
