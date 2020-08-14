node_name = "consul-server-1"
server = true
bootstrap_expect = 1
bind_addr = "192.168.33.10"
data_dir = "/opt/consul/"

cert_file = "/etc/consul.d/consul-server.cert.pem"
key_file = "/etc/consul.d/consul-server.key.pem"
ca_file = "/etc/consul.d/ca.cert.pem"
verify_incoming = true
verify_outgoing = true
verify_server_hostname = true

recursors = ["8.8.8.8", "8.8.4.4"]

retry_join = []

ui = true
client_addr = "0.0.0.0"

ports {
  grpc = 8502
  http = -1
  https = 8501
}

connect {
  enabled = true
}

auto_encrypt {
  allow_tls = true
}
