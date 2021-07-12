#!/usr/bin/env bash
curl -v -X PUT http://localhost:8080/message/ -H "Content-Type: application/json" --data '{"id": 3, "text": "Darkest Dungeon is a good game"}'