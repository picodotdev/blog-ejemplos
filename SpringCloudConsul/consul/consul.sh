#!/usr/bin/env bash
consul agent -server -client=0.0.0.0 -data-dir /tmp/consul -ui-dir ./webui
