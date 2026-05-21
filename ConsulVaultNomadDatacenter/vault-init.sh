#!/usr/bin/env bash
$ export VAULT_ADDR="https://192.168.33.20:8200"
$ export VAULT_CLIENT_CERT="server-certs/certs/vault-agent.cert.pem"
$ export VAULT_CLIENT_KEY="server-certs/private/vault-agent.key.pem"
$ export VAULT_CACERT="ca/intermediate/certs/ca-chain.cert.pem"
$ vault operator init -key-shares=5 -key-threshold=2
$ vault operator unseal GmSsvWlRtimeVb4ikBYKGJeAWGgkB1h8NpLpGEu0oqTe
$ vault operator unseal zPBru7ivXOhOZmytYHN5gFhusX2kpNR5TOgvxrjxnL+B
