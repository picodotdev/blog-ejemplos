job "discoveryserver" {
  datacenters = ["localhost"]

  type = "service"

  group "services" {
    count = 1

    task "discoveryserver" {
      driver = "docker"

      config {
        image = "openjdk:8-slim"
        work_dir = "/app"
        command  = "java"
        args  = ["-jar", "discoveryserver.jar", "--args=\"--port=8761\""]
        volumes = [
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloud/discoveryserver/build/libs/:/app/"
        ]
        network_mode = "host"
        port_map {
          port = 8761
        }
      }

      resources {
        cpu    = 500 # MHz
        memory = 1024 # MB

        network {
          port "port" {
            static = 8761 
          }
        }
      }
    }
  }
}

