#!/usr/bin/env bash
ansible-playbook -i hosts -l raspberrypi --tags system-install site-system-install.yml
