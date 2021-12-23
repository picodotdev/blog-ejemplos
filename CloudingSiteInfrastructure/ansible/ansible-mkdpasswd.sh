#!/usr/bin/env bash
ansible all -i localhost, -m debug -a "msg={{ 'ubuntu' | password_hash('sha512', 'G7g9dNWz') }}"