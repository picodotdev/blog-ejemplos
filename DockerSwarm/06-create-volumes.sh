#!/usr/bin/env bash

export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

docker volume create --driver=rexray --name=postgresql --opt=size=5
docker volume create --driver=rexray --name=app --opt=size=5

echo -e "\n# Volumes"
docker volume ls
