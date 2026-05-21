#!/usr/bin/env bash
docker network create --subnet 172.30.0.0/16 nomad

consul agent -dev -ui -client=0.0.0.0
nomad agent -dev -config=nomad/nomad.conf
# http://127.0.0.1:8500
# http://127.0.0.1:4646

./gradlew assemble

nomad job run nomad/traefik.nomad
# http://127.0.0.1:8092
nomad job run nomad/zipkin.nomad
# http://127.0.0.1:9411

nomad job run nomad/configserver.nomad
nomad job run nomad/service.nomad
nomad job run nomad/client.nomad

curl http://127.0.0.1:8093/configuration/service-dev.yml
curl http://127.0.0.1:8093/service

nomad job run nomad/service.nomad
nomad job promote service
nomad job revert service 0

curl -X POST http://localhost:8093/service/actuator/refresh
curl http://127.0.0.1:8093/service/actuator/prometheus | grep "service.invocations"