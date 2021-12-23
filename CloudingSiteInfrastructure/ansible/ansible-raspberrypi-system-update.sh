#!/usr/bin/env bash
ansible-playbook -i hosts -l raspberrypi --tags system-update site-system-update.yml
