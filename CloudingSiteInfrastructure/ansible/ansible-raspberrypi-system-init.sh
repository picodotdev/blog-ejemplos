#!/usr/bin/env bash
source terraform-env.conf
ansible-playbook -i hosts -l raspberrypi --tags system-init site-system-init.yml
