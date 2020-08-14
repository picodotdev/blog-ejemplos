name = "nomad-server-1"
data_dir = "/opt/nomad"

advertise {
  http = "192.168.33.30"
  rpc  = "192.168.33.30"
  serf = "192.168.33.30"
}

server {
  enabled = true
  bootstrap_expect = 1
  encrypt = "aUcxnl/42fcutmLyRO/TVg=="
}

consul {
  address = "127.0.0.1:8501"
  cert_file = "/etc/consul.d/consul-agent.cert.pem"
  key_file = "/etc/consul.d/consul-agent.key.pem"
  ca_file = "/etc/consul.d/ca.cert.pem"
  ssl = true
  server_auto_join = true
  client_auto_join = true
}

vault {
  enabled = true
  address = "https://vault.service.consul:8200"
  token = "$VAULT_TOKEN"
  create_from_role = "nomad-cluster"

  cert_file = "/etc/nomad.d/vault-agent.cert.pem"
  key_file = "/etc/nomad.d/vault-agent.key.pem"
  ca_file = "/etc/nomad.d/ca.cert.pem"  
}

tls {
  http = true
  rpc  = true
  cert_file = "/etc/nomad.d/nomad-server.cert.pem"
  key_file = "/etc/nomad.d/nomad-server.key.pem"
  ca_file = "/etc/nomad.d/ca.cert.pem"
  tls_min_version = "tls12"
  verify_https_client = true
  verify_server_hostname = true
}
