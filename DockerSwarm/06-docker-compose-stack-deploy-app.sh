#!/usr/bin/env bash

export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

docker volume create --driver rexray --opt size=1 --name app

docker stack deploy -c docker-compose-stack-app.yml app

sleep 30s

echo -e "\n# Cluster services"
docker service ls
echo -e "\n# Nginx service tasks"
docker service ps app_nginx
echo -e "\n# App service tasks"
docker service ps app_app

for i in "01" "02" "03"; do
	echo -e "\n# Node $i containers"
	eval $(docker-machine env node-$i)
	docker ps
done
