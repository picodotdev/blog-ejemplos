#!/usr/bin/env bash

VBoxManage setproperty websrvauthlibrary null
vboxwebsrv -H 0.0.0.0 -v
