#!/usr/bin/env bash
source ansible-env.conf
ansible-playbook -i hosts -l vagrant --tags system-init --extra-vars "ansible_user=vagrant ansible_ssh_private_key_file=../vagrant/.vagrant/machines/default/virtualbox/private_key" site-system-init.yml
