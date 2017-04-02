#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"

NODE=$(docker service ps -f desired-state=running app_app | tail -n 1 | awk '{print $4}')
eval $(docker-machine env $NODE)
docker rm -f $(docker ps -qa -f label=com.docker.swarm.service.name=app_app)

sleep 3s

eval $(docker-machine env node-01)
echo -e "\n# App service tasks"
docker service ps app_app

for i in "01" "02" "03"; do
	echo -e "\n# Node $i containers"
	eval $(docker-machine env node-$i)
	docker ps
done
