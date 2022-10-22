Kind = "ingress-gateway"
Name = "ingress-gateway"

Listeners = [{
    Port = 8080
    Protocol = "http"
    Services = [{
        Name = "frontend"
        Hosts = ["localhost", "192.168.56.10"]
    }]
}]
