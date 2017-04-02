#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"

NODE=$(docker service ps -f desired-state=running app_app | tail -n 1 | awk '{print $4}')
eval $(docker-machine env $NODE)
docker logs $(docker ps -qa -f label=com.docker.swarm.service.name=app_app)
