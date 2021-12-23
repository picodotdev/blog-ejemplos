#!/usr/bin/env bash
source ansible-env.conf
ansible-playbook -i hosts -l clouding --tags content-update site-content-update.yml
