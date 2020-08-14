node_name = "nomad-agent-1"
server = false
bind_addr = "192.168.33.40"
data_dir = "/opt/consul/"

cert_file = "/etc/consul.d/consul-agent.cert.pem"
key_file = "/etc/consul.d/consul-agent.key.pem"
ca_file = "/etc/consul.d/ca.cert.pem"
verify_incoming = true
verify_outgoing = true
verify_server_hostname = true

recursors = ["8.8.8.8", "8.8.4.4"]

retry_join = ["192.168.33.10"]

ports {
  grpc = 8502
  http = -1
  https = 8501
}

auto_encrypt {
  tls = true
}
