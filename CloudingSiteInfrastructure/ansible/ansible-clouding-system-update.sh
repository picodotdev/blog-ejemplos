#!/usr/bin/env bash
source ansible-env.conf
ansible-playbook -i hosts -l clouding --tags system-update site-system-update.yml
