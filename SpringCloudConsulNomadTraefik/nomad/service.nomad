job "service" {
  datacenters = ["dc1"]

  group "service" {
    count = 2

    update {
      max_parallel      = 1
      health_check      = "checks"
      min_healthy_time  = "20s"
      healthy_deadline  = "10m"
      progress_deadline = "20m"
      canary            = 1
      stagger           = "15s"
    }

    task "service" {
      driver = "docker"
      config {
        image = "openjdk:11-jdk"
        args = [
          "bash",
          "-c",
          "(cd /app && java -jar /app/service/build/libs/service-1.0.jar --port=8080)"
        ]
        port_map {
          http = "8080"
        }
        network_mode = "nomad"
        extra_hosts = [
          "traefik:172.30.0.3"
        ]
        volumes = [
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloudConsulNomadTraefik/:/app"
        ]
        labels {
          traefik.http.middlewares.service1-stripprefix.stripprefix.prefixes="/service",
          traefik.http.middlewares.service1-retry.retry.attempts="10",
          traefik.http.routers.service1.middlewares="service1-stripprefix,service1-retry",
          traefik.http.routers.service1.rule="PathPrefix(`/service`)",
          traefik.http.services.service1.loadbalancer.server.port="8080"
        }
      }

      service {
        name = "service"
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
          port "http" {}
        }
      }
    }
  }
}