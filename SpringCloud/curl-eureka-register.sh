#!/usr/bin/env bash
curl -v -X POST -H "Accept: application/json" -H "Content-type: application/json;charset=utf-8" --data "@eureka-register.json" http://localhost:8761/eureka/apps/app

curl -XGET -H "Accept: application/json" -H "Content-type: application/json;charset=utf-8" http://localhost:8761/eureka/apps

