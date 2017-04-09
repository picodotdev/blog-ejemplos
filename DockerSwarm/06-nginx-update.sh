#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

echo -e "\n# Nginx service tasks"
docker service ps nginx

echo -e "\n# Nginx service inspect"
docker service inspect --pretty nginx

docker service update --image nginx:1.10-alpine nginx

sleep 1m

echo -e "\n# Nginx service tasks"
docker service ps nginx

echo -e "\n# Nginx service inspect"
docker service inspect --pretty nginx

