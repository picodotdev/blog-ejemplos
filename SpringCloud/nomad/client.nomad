job "client" {
  datacenters = ["localhost"]

  type = "service"

  group "services" {
    count = 1

    task "client" {
      driver = "docker"

      config {
        image = "openjdk:8-slim"
        work_dir = "/app"
        command  = "java"
        args  = ["-jar", "client.jar", "--service=resilience4jproxy"]
        volumes = [
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloud/client/build/libs/:/app/"
        ]
        network_mode = "host"
      }
    }
  }
}

