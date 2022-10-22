service {
    name = "ingress-gateway"
    kind = "ingress-gateway"
    port = 20000

    checks = [{
        name = "ingress-gateway-check"
        tcp = "localhost:20000"
        interval = "10s"
    }]
    
    meta {
        prometheus_port = "20202"
    }
    
    proxy {
        config {
            envoy_prometheus_bind_addr = "0.0.0.0:20202"
        }
    }
}
