job "traefik" {
  datacenters = ["dc1"]

  group "traefik" {
    task "traefik" {
      driver = "docker"
      config {
        image = "traefik"
        args = [
          "--api.insecure=true",
          "--providers.consulcatalog.endpoint.address=http://172.30.0.1:8500"
        ]
        network_mode = "nomad"
        ipv4_address = "172.30.0.3"
        port_map {
          lb = 80
          ui = 8080
        }        
      }

      service {
        name = "traefik"
        port = "lb"

        check {
          type     = "http"
          port     = "ui"
          path     = "/"
          interval = "5s"
          timeout  = "2s"
        }
      }

      resources {
        cpu    = 200
        memory = 128
        network {
          mbits = 20
          port "lb" {
            static = 8093
          }
          port "ui" {
            static = 8092
          }
        }
      }
    }
  }
}