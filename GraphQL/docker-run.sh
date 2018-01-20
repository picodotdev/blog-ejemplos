#!/usr/bin/env bash

docker run -it --rm -p 8080:8080 -v `pwd`:/app openjdk:9-slim bash