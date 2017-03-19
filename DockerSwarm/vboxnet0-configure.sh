#!/usr/bin/env bash

sudo ip addr add 192.168.99.1/24 dev vboxnet0
sudo ip link set dev vboxnet0 up
