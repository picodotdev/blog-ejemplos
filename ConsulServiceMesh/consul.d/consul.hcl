server = true
bootstrap_expect = 1
bind_addr = "127.0.0.1"
client_addr = "0.0.0.0"

ports {
    grpc = 8502
}

connect {
    enabled = true
}

ui_config {
    enabled = true
    metrics_provider = "prometheus"
    metrics_proxy {
        base_url = "http://localhost:9090"
    }
}
