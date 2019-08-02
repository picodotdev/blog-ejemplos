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
        command  = "java"
        args  = ["-jar", "configserver.jar", "--args=\"--port=8090\""]
        volumes = [
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloud/configserver/build/libs/:/app/",
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloud/configserver/misc/:/app/misc/"
        ]
        network_mode = "host"
        port_map {
          port = 8090
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

