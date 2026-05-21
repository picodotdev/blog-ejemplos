FROM ubuntu:20.04
MAINTAINER picodotdev <pico.dev@gmail.com>

RUN apt-get -y update && \
    apt-get -y install nginx
RUN ln -sf /dev/stdout /var/log/nginx/access.log \
    && ln -sf /dev/stderr /var/log/nginx/error.log

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

