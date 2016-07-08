#!/usr/bin/env bash
docker run --rm --name nginx -p 80:80 -p 443:443 -v `pwd`/nginx.conf:/etc/nginx/conf.d/default.conf:ro -v `pwd`/localhost.key:/etc/nginx/localhost.key:ro -v `pwd`/localhost.pem:/etc/nginx/localhost.pem:ro nginx
