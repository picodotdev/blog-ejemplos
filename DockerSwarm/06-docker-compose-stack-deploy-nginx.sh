#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

docker stack deploy -c docker-compose-stack-nginx.yml nginx

sleep 30s

echo -e "\n# Cluster services"
docker service ls
echo -e "\n# Nginx service tasks"
docker service ps nginx_nginx

for i in "01" "02" "03"; do
	echo -e "\n# Node $i containers"
	eval $(docker-machine env node-$i)
	docker ps
done
