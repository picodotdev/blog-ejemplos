job "proxy" {
  datacenters = ["localhost"]

  type = "service"

  group "services" {
    count = 1

    task "proxy" {
      driver = "docker"

      config {
        image = "openjdk:8-slim"
        work_dir = "/app"
        command  = "java"
        args  = ["-jar", "proxy.jar", "--args=\"--port=8085\""]
        volumes = [
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloud/proxy/build/libs/:/app/"
        ]
        network_mode = "host"
        port_map {
          port = 8085
        }
      }

      resources {
        cpu    = 500 # MHz
        memory = 1024 # MB

        network {
          port "port" {
            static = 8085
          }
        }
      }
    }
  }
}

