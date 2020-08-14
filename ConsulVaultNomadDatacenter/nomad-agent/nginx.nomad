job "nginx" {
  datacenters = ["dc1"]

  group "services" {
    count = 1

    task "nginx" {
      driver = "docker"

      config {
        image = "nginx:alpine"

         network_mode = "bridge"
         port_map {
           http = 80
         }
      }

      resources {
        memory = 512 # MB

        network {
          port "http" {
            static = 8080
          }
        }
      }

      service {
        name = "nginx"
        port = "http"
        check {
          name     = "nginx-check"
          type     = "tcp"
          interval = "10s"
          timeout  = "2s"
        }
      }
    }
  }
}