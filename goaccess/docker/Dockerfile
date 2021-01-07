FROM ubuntu:20.04
MAINTAINER picodotdev <pico.dev@gmail.com>

RUN apt-get -y update && apt-get -y install goaccess

VOLUME /var/www/goaccess
EXPOSE 7890

CMD ["/bin/goaccess"]
