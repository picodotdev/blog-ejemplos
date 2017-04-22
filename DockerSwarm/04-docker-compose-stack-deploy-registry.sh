#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

docker volume create --driver rexray --name registry --opt size=5

docker stack deploy -c docker-compose-stack-registry.yml registry

sleep 30s

echo -e "\n# Cluster services"
docker service ls
echo -e "\n# Registry service tasks"
docker service ps registry_registry

for i in "01" "02" "03"; do
	echo -e "\n# Node $i containers"
	eval $(docker-machine env node-$i)
	docker ps
done
