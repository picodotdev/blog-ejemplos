#!/usr/bin/env bash

export MACHINE_STORAGE_PATH="/run/media/picodotdev/BMOVE ROJO/docker-machine/"

#sudo ufw allow to any port 18083 from 192.168.0.0/16  

for i in "01" "02" "03"; do
	docker-machine ssh node-$i "curl -sSL https://dl.bintray.com/emccode/rexray/install | sh -"
	docker-machine ssh node-$i "cat > /tmp/rexray-config.yml << EOF
rexray:
  logLevel: info
libstorage:
  service: virtualbox
virtualbox:
  endpoint: http://192.168.99.1:18083
  volumePath: /run/media/picodotdev/BMOVE ROJO/docker-machine/volumes/
  controllerName: SATA
EOF
"
	docker-machine ssh node-$i "sudo mkdir -p /etc/rexray/"
	docker-machine ssh node-$i "sudo mv /tmp/rexray-config.yml /etc/rexray/config.yml"
	docker-machine ssh node-$i "sudo rexray start"
done

#http://rexray.readthedocs.io/en/stable/user-guide/demo/
#docker volume create --driver rexray --opt size=1 --name myvolume
#docker run -it --rm --name nginx --volume-driver=rexray -v myvolume:/xxx nginx:stable-alpine
#docker run -it --rm --volume-driver=rexray -v myvolume1:/myvolume --name busybox busybox /bin/bash
#docker run -tid --volume-driver=rexray -v myvolume:/myvolume --name temp01 busybox
#docker-machine ssh node-$i "sudo cat /var/log/rexray/rexray.log"
