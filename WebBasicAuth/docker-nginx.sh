#!/usr/bin/env bash
docker run --rm -p 80:80 -p 443:443 \
  -v `pwd`/nginx-default.conf:/etc/nginx/conf.d/default.conf:ro \
  -v `pwd`/localhost.crt:/etc/nginx/conf.d/localhost.crt:ro \
  -v `pwd`/localhost.key:/etc/nginx/conf.d/localhost.key:ro \
  -v `pwd`/.localhost.htpasswd:/etc/nginx/conf.d/.localhost.htpasswd:ro \
  nginx:alpine
