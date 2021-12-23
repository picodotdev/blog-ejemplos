#!/usr/bin/env bash
source ansible-env.conf
ansible-playbook -i hosts -l vagrant --tags system-update site-system-update.yml
