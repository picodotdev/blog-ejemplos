#!/usr/bin/env bash
ansible-playbook -i hosts -l raspberrypi --tags "content-update,content-update-git" site-content-update.yml
