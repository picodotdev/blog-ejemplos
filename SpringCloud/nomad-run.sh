#!/usr/bin/env bash
consul agent -dev -datacenter localhost
sudo nomad agent -dev -dc localhost

nomad job run nomad/discoveryserver.nomad
nomad job run nomad/configserver.nomad
nomad job run nomad/service.nomad
nomad job run nomad/proxy.nomad
nomad job run nomad/client.nomad
