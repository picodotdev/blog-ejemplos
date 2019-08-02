job "configserver" {
  datacenters = ["localhost"]

  type = "service"

  group "services" {
    count = 1

    task "configserver" {
      driver = "docker"

      config {
        image = "openjdk:8-slim"
        work_dir = "/app"
        command  = "./gradlew"
        args  = ["configserver:run", "--args=\"--port=8090\""]
        volumes = [
          "/home/picodotdev/.gradle:/root/.gradle",
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloud:/app"
        ]
        port_map {
          port = 8761
        }
      }

      resources {
        cpu    = 500 # MHz
        memory = 1024 # MB

        network {
          port "port" {
            static = 8090 
          }
        }
      }
    }
  }
}

