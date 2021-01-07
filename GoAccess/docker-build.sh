docker build -t "picodotdev/goaccess:1.0" docker/
docker run -it --rm -v "${PWD}/nginx/log:/var/log/nginx" picodotdev/goaccess:1.0 goaccess /var/log/nginx/nginx-access.log /var/log/nginx/goaccess-access.log --log-format=VCOMBINED

