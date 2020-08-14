ui = true
api_addr = "https://192.168.33.20:8200"

service_registration "consul" {
  scheme = "https"
  address = "127.0.0.1:8501"
  tls_cert_file = "/etc/vault.d/consul-agent.cert.pem"
  tls_key_file = "/etc/vault.d/consul-agent.key.pem"
  tls_ca_file = "/etc/vault.d/ca.cert.pem"
  tls_min_version = "tls13"
}

storage "consul" {
  scheme = "https"
  address = "127.0.0.1:8501"
  path = "vault/"
  tls_cert_file = "/etc/vault.d/consul-agent.cert.pem"
  tls_key_file = "/etc/vault.d/consul-agent.key.pem"
  tls_ca_file = "/etc/vault.d/ca.cert.pem"
  tls_min_version = "tls13"
}

listener "tcp" {
  address = "0.0.0.0:8200"
  tls_cert_file = "/etc/vault.d/vault-server.cert.pem"
  tls_key_file = "/etc/vault.d/vault-server.key.pem"
  tls_client_ca_file = "/etc/vault.d/ca.cert.pem"
  tls_min_version = "tls13"
}
