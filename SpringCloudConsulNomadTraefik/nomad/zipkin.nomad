job "zipkin" {
  datacenters = ["dc1"]

  group "zipkin" {

    task "zipkin" {
      driver = "docker"
      config {
        image = "openzipkin/zipkin"
        port_map {
          http = "9411"
        }
        network_mode = "nomad"
        ipv4_address = "172.30.0.4"
      }
      service {
        name = "zipkin"
        port = "http"

        tags = [
          "traefik.http.middlewares.zipkin-retry.retry.attempts=10",
          "traefik.http.routers.zipkin.middlewares=zipkin-retry",
          "traefik.http.routers.zipkin.rule=PathPrefix(`/zipkin`)",
          "traefik.http.services.zipkin.loadbalancer.server.port=9411"
        ]
      }

      resources {
        cpu    = 200
        memory = 1024
        network {
          mbits = 20
          port "http" {
            static = 9411
          }
        }
      }
    }
  }
}
