#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

echo -e "\n# Nginx service tasks"
docker service ps nginx

echo -e "\n# Nginx service inspect"
docker service inspect --pretty nginx

docker service scale nginx=4

sleep 1m

echo -e "\n# Nginx service tasks"
docker service ps nginx

echo -e "\n# Nginx service inspect"
docker service inspect --pretty nginx

