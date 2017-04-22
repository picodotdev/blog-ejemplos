#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

docker volume rm postgres
docker volume rm app

