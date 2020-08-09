#!/usr/bin/env bash
docker run --rm -p 80:80 \
  -v `pwd`/nginx-www.127.0.0.1.xip.io.conf:/etc/nginx/conf.d/www.127.0.0.1.xip.io.conf:ro \
  nginx:latest
