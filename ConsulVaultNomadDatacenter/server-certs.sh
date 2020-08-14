#!/usr/bin/env bash
set -e

CA_PATH=../ca
CERTS_PATH=server-certs

export CA_DIR=$CA_PATH
export INTERMEDIATE_DIR=$CA_PATH/intermediate

mkdir -p $CERTS_PATH/certs $CERTS_PATH/csr $CERTS_PATH/private
cp openssl/server/openssl*.conf $CERTS_PATH/
cd $CERTS_PATH

# Consul Server

openssl genrsa \
      -out private/consul-server.key.pem 8192
chmod 400 private/consul-server.key.pem

openssl req -config openssl-consul-server.conf \
      -key private/consul-server.key.pem \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=Consul Server" \
      -new -sha256 -out csr/consul-server.csr.pem
      
openssl ca -config $CA_PATH/intermediate/openssl.conf \
      -extensions client_server_cert -batch -days 1825 -notext -md sha256 \
      -in csr/consul-server.csr.pem \
      -out certs/consul-server.cert.pem
chmod 444 certs/consul-server.cert.pem

cat certs/consul-server.cert.pem $CA_PATH/intermediate/certs/intermediate.cert.pem > certs/consul-server-chain.cert.pem

openssl x509 -noout -text \
      -in certs/consul-server.cert.pem
openssl verify -CAfile $CA_PATH/intermediate/certs/ca-chain.cert.pem \
      certs/consul-server.cert.pem

# Consul Agent

openssl genrsa \
      -out private/consul-agent.key.pem 8192
chmod 400 private/consul-agent.key.pem

openssl req -config openssl-consul-agent.conf \
      -key private/consul-agent.key.pem \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=Consul Agent" \
      -new -sha256 -out csr/consul-agent.csr.pem
      
openssl ca -config $CA_PATH/intermediate/openssl.conf \
      -extensions client_server_cert -batch -days 1825 -notext -md sha256 \
      -in csr/consul-agent.csr.pem \
      -out certs/consul-agent.cert.pem
chmod 444 certs/consul-agent.cert.pem

cat certs/consul-agent.cert.pem $CA_PATH/intermediate/certs/intermediate.cert.pem > certs/consul-agent-chain.cert.pem

openssl x509 -noout -text \
      -in certs/consul-agent.cert.pem
openssl verify -CAfile $CA_PATH/intermediate/certs/ca-chain.cert.pem \
      certs/consul-agent.cert.pem

# Vault Server

openssl genrsa \
      -out private/vault-server.key.pem 8192
chmod 400 private/vault-server.key.pem

openssl req -config openssl-vault-server.conf \
      -key private/vault-server.key.pem \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=Vault Server" \
      -new -sha256 -out csr/vault-server.csr.pem
      
openssl ca -config $CA_PATH/intermediate/openssl.conf \
      -extensions client_server_cert -batch -days 1825 -notext -md sha256 \
      -in csr/vault-server.csr.pem \
      -out certs/vault-server.cert.pem
chmod 444 certs/vault-server.cert.pem

cat certs/vault-server.cert.pem $CA_PATH/intermediate/certs/intermediate.cert.pem > certs/vault-server-chain.cert.pem

openssl x509 -noout -text \
      -in certs/vault-server.cert.pem
openssl verify -CAfile $CA_PATH/intermediate/certs/ca-chain.cert.pem \
      certs/vault-server.cert.pem
      
# Vault Agent

openssl genrsa \
      -out private/vault-agent.key.pem 8192
chmod 400 private/vault-agent.key.pem

openssl req -config openssl-agent.conf \
      -key private/vault-agent.key.pem \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=Vault Agent" \
      -new -sha256 -out csr/vault-agent.csr.pem
      
openssl ca -config $CA_PATH/intermediate/openssl.conf \
      -extensions client_server_cert -batch -days 1825 -notext -md sha256 \
      -in csr/vault-agent.csr.pem \
      -out certs/vault-agent.cert.pem
chmod 444 certs/vault-agent.cert.pem

cat certs/vault-agent.cert.pem $CA_PATH/intermediate/certs/intermediate.cert.pem > certs/vault-agent-chain.cert.pem

openssl x509 -noout -text \
      -in certs/vault-agent.cert.pem
openssl verify -CAfile $CA_PATH/intermediate/certs/ca-chain.cert.pem \
      certs/vault-agent.cert.pem

# Nomad Server

openssl genrsa \
      -out private/nomad-server.key.pem 8192
chmod 400 private/nomad-server.key.pem

openssl req -config openssl-nomad-server.conf \
      -key private/nomad-server.key.pem \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=Nomad Server" \
      -new -sha256 -out csr/nomad-server.csr.pem
      
openssl ca -config $CA_PATH/intermediate/openssl.conf \
      -extensions client_server_cert -batch -days 1825 -notext -md sha256 \
      -in csr/nomad-server.csr.pem \
      -out certs/nomad-server.cert.pem
chmod 444 certs/nomad-server.cert.pem

cat certs/nomad-server.cert.pem $CA_PATH/intermediate/certs/intermediate.cert.pem > certs/nomad-server-chain.cert.pem

openssl x509 -noout -text \
      -in certs/nomad-server.cert.pem
openssl verify -CAfile $CA_PATH/intermediate/certs/ca-chain.cert.pem \
      certs/nomad-server.cert.pem
      
# Nomad Agent

openssl genrsa \
      -out private/nomad-agent.key.pem 8192
chmod 400 private/nomad-agent.key.pem

openssl req -config openssl-nomad-agent.conf \
      -key private/nomad-agent.key.pem \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=Nomad Agent" \
      -new -sha256 -out csr/nomad-agent.csr.pem
      
openssl ca -config $CA_PATH/intermediate/openssl.conf \
      -extensions client_server_cert -batch -days 1825 -notext -md sha256 \
      -in csr/nomad-agent.csr.pem \
      -out certs/nomad-agent.cert.pem
chmod 444 certs/nomad-agent.cert.pem

cat certs/nomad-agent.cert.pem $CA_PATH/intermediate/certs/intermediate.cert.pem > certs/nomad-agent-chain.cert.pem

openssl x509 -noout -text \
      -in certs/nomad-agent.cert.pem
openssl verify -CAfile $CA_PATH/intermediate/certs/ca-chain.cert.pem \
      certs/nomad-agent.cert.pem

# UI Server

openssl genrsa \
      -out private/ui-server.key.pem 8192
chmod 400 private/ui-server.key.pem

openssl req -config openssl-ui-server.conf \
      -key private/ui-server.key.pem \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=UI Server" \
      -new -sha256 -out csr/ui-server.csr.pem
      
openssl ca -config $CA_PATH/intermediate/openssl.conf \
      -extensions server_cert -batch -days 1825 -notext -md sha256 \
      -in csr/ui-server.csr.pem \
      -out certs/ui-server.cert.pem
chmod 444 certs/ui-server.cert.pem

cat certs/ui-server.cert.pem $CA_PATH/intermediate/certs/intermediate.cert.pem > certs/ui-server-chain.cert.pem

openssl x509 -noout -text \
      -in certs/ui-server.cert.pem
openssl verify -CAfile $CA_PATH/intermediate/certs/ca-chain.cert.pem \
      certs/ui-server.cert.pem
