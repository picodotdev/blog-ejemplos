#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"
eval $(docker-machine env node-01)

(cd ./SpringBootApp; ./gradlew build; cp build/libs/SpringBootApp-1.0.jar misc/)
(cd ./SpringBootApp/misc; docker build -t spring-boot-app:1.0 .)


echo -e "\n# Docker images"
docker images
docker tag spring-boot-app:1.0 localhost:5000/spring-boot-app:1.0
docker push localhost:5000/spring-boot-app:1.0
