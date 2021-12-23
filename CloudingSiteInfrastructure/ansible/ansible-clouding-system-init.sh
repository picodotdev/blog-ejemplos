#!/usr/bin/env bash
source ansible-env.conf
ansible-playbook -i hosts -l clouding --tags system-init --extra-vars "ansible_user=root ansible_ssh_private_key_file=~/.ssh/pico.dev@gmail.com" site-system-init.yml
