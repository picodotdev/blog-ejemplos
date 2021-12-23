#!/usr/bin/env bash
source ansible-env.conf
ansible-playbook -i hosts -l vagrant --tags system-install site-system-install.yml
