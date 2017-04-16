#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

docker stack deploy -c docker-compose-stack-postgres.yml postgres

sleep 30s

echo -e "\n# Cluster services"
docker service ls
echo -e "\n# Postgres service tasks"
docker service ps postgres_postgres

for i in "01" "02" "03"; do
	echo -e "\n# Node $i containers"
	eval $(docker-machine env node-$i)
	docker ps
done
