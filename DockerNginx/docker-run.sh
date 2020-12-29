#!/usr/bin/env bash
docker run -it --rm \
    -v "${PWD}/www.127.0.0.1.xip.io.conf:/etc/nginx/conf.d/www.127.0.0.1.xip.io.conf:ro" \
    -v "${PWD}/nginx/log:/var/log/nginx" \
    -p "80:80" \
    picodotdev/nginx:1.0
