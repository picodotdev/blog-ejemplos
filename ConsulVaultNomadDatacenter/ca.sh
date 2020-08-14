#!/usr/bin/env bash
set -e

CA_PATH=ca

export CA_DIR=.
export INTERMEDIATE_DIR=./intermediate

mkdir -p $CA_PATH/certs $CA_PATH/crl $CA_PATH/newcerts $CA_PATH/private $CA_PATH/intermediate/
cp openssl/ca/openssl*.conf $CA_PATH/
cp openssl/intermediate/openssl*.conf $CA_PATH/intermediate/
cd $CA_PATH

chmod 700 private
touch index.txt
echo 1000 > serial

# CA

openssl genrsa -out private/ca.key.pem 8192
chmod 400 private/ca.key.pem

openssl req -config openssl.conf \
      -key private/ca.key.pem \
      -new -x509 -days 7300 -sha256 -extensions v3_ca \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=Acme S.A. (CA)" \
      -out certs/ca.cert.pem

# Intermediate CA

mkdir -p intermediate/certs intermediate/crl intermediate/csr intermediate/newcerts intermediate/private
chmod 700 intermediate/private
touch intermediate/index.txt
echo 1000 > intermediate/serial
echo 1000 > intermediate/crlnumber

openssl genrsa -out intermediate/private/intermediate.key.pem 8192
chmod 400 intermediate/private/intermediate.key.pem

openssl req -config intermediate/openssl.conf -new -sha256 \
      -key intermediate/private/intermediate.key.pem \
      -subj "/C=ES/ST=Spain/L=Madrid/O=Acme S.A./CN=Acme S.A. (Intermediate CA)" \
      -out intermediate/csr/intermediate.csr.pem

openssl ca -config openssl.conf \
      -extensions v3_intermediate_ca -days 3650 -batch -notext -md sha256 \
      -in intermediate/csr/intermediate.csr.pem \
      -out intermediate/certs/intermediate.cert.pem

openssl x509 -noout -text \
      -in intermediate/certs/intermediate.cert.pem
openssl verify -CAfile certs/ca.cert.pem \
      intermediate/certs/intermediate.cert.pem

cat intermediate/certs/intermediate.cert.pem \
      certs/ca.cert.pem > intermediate/certs/ca-chain.cert.pem
chmod 444 intermediate/certs/ca-chain.cert.pem