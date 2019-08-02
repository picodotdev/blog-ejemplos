job "service" {
  datacenters = ["localhost"]

  type = "service"

  group "services" {
    count = 1

    task "service" {
      driver = "docker"

      config {
        image = "openjdk:8-slim"
        work_dir = "/app"
        command  = "java"
        args  = ["-jar", "service.jar", "--args=\"--port=8081\""]
        volumes = [
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloud/service/build/libs/:/app/"
        ]
        network_mode = "host"
        port_map {
          port = 8081
        }
      }

      resources {
        cpu    = 500 # MHz
        memory = 1024 # MB

        network {
          port "port" {
            static = 8081
          }
        }
      }
    }
  }
}

