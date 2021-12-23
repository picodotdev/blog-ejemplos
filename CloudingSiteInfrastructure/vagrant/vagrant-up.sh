#!/usr/bin/env bash
export VAGRANT_EXPERIMENTAL="cloud_init,disks"
vagrant up --provision
