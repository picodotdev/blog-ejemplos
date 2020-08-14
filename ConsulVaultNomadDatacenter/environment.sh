#!/usr/bin/env bash
set -e

export CONSUL_HTTP_ADDR="https://192.168.33.10:8501"
export CONSUL_CLIENT_CERT="server-certs/certs/consul-agent.cert.pem"
export CONSUL_CLIENT_KEY="server-certs/private/consul-agent.key.pem"
export CONSUL_CACERT="ca/intermediate/certs/ca-chain.cert.pem"
export CONSUL_HTTP_SSL=true
export CONSUL_HTTP_SSL_VERIFY=true

export VAULT_ADDR="https://192.168.33.20:8200"
export VAULT_CLIENT_CERT="server-certs/certs/vault-agent.cert.pem"
export VAULT_CLIENT_KEY="server-certs/private/vault-agent.key.pem"
export VAULT_CACERT="ca/intermediate/certs/ca-chain.cert.pem"

export NOMAD_ADDR="https://192.168.33.30:4646"
export NOMAD_CLIENT_CERT="server-certs/certs/nomad-agent.cert.pem"
export NOMAD_CLIENT_KEY="server-certs/private/nomad-agent.key.pem"
export NOMAD_CACERT="ca/intermediate/certs/ca-chain.cert.pem"

consul members
consul catalog services
nomad server members
nomad node status
nomad job status