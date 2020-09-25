#!/usr/bin/env bash
docker run --rm --network elk docker.elastic.co/beats/filebeat:7.9.1 setup -E setup.kibana.host=kibana:5601 -E output.elasticsearch.hosts=["elasticsearch:9200"]
