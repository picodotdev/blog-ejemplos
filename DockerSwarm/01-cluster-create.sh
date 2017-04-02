#!/usr/bin/env bash

export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"

for i in "01" "02" "03"; do
	docker-machine create -d virtualbox node-$i
done

MANAGER_IP=$(docker-machine ip node-01)
eval $(docker-machine env node-01)

docker swarm init --advertise-addr $MANAGER_IP

MANAGER_TOKEN=$(docker swarm join-token -q manager)
WORKER_TOKEN=$(docker swarm join-token -q worker)

for i in "02" "03";do
	WORKER_IP=$(docker-machine ip node-$i)
	eval $(docker-machine env node-$i)

	docker swarm join --token $WORKER_TOKEN --advertise-addr $WORKER_IP $MANAGER_IP:2377
done

eval $(docker-machine env node-01)

echo -e "\n# Machines"
docker-machine ls
echo -e "\n# Nodes"
docker node ls
