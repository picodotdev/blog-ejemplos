#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

docker network create --driver overlay nginx
docker network ls -f "driver=overlay"

docker service create --name nginx -p 80:80 -p 443:443 --network nginx nginx:stable-alpine
sleep 1m

docker service create --name util --network nginx --mode global alpine sleep 1000000000
sleep 1m

docker service ls
docker service ps nginx
docker service ps util

UTIL_CONTAINER_ID=$(docker ps -q --filter label=com.docker.swarm.service.name=util)
docker exec -it $UTIL_CONTAINER_ID apk add --update drill
docker exec -it $UTIL_CONTAINER_ID drill nginx
docker service rm util

sleep 5s

for i in "01" "02" "03"; do
	eval $(docker-machine env node-$i)
	docker ps
done

eval $(docker-machine env node-01)
sleep 5

for i in "01" "02" "03"; do
    NODE_IP=$(docker-machine ip node-$i)
    curl http://$NODE_IP/
done
