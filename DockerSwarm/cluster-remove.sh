#!/usr/bin/env bash

#export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"

for i in "01" "02" "03"; do
	docker-machine rm -f node-$i
done

