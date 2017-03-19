#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

docker volume create --driver rexray --opt size=1 --name spring-boot-app

docker stack deploy -c docker-compose-stack.yml nginx

sleep 30s

docker service ls
docker service ps nginx_nginx

for i in "01" "02" "03"; do
	docker ps

    NODE_IP=$(docker-machine ip node-$i)
    curl http://$NODE_IP/
done
