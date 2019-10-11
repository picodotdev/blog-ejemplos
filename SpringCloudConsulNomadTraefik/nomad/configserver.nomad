job "configserver" {
  datacenters = ["dc1"]

  group "configserver" {
    task "configserver" {
      driver = "docker"
      config {
        image = "openjdk:11-jdk"
        args = [
          "bash",
          "-c",
          "(cd /app/configserver && java -jar /app/configserver/build/libs/configserver-1.0.jar)"
        ]
        network_mode = "nomad"
        port_map {
          http = 8888
        }
        volumes = [
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloudConsulNomadTraefik/:/app"
        ]
        labels {
          traefik.http.middlewares.configserver1-stripprefix.stripprefix.prefixes="/configuration",
          traefik.http.routers.configserver1.middlewares="configserver1-stripprefix"
          traefik.http.routers.configserver1.rule="PathPrefix(`/configuration`)",
        }
      }

      service {
        name = "configserver"
        port = "http"

        check {
          type     = "http"
          port     = "http"
          path     = "/actuator/health"
          interval = "5s"
          timeout  = "2s"
        }
      }

      resources {
        cpu    = 200
        memory = 1024
        network {
          mbits = 20
          port "http" {
            static = 8888
          }
        }
      }
    }
  }
}