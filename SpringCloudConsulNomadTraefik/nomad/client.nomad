job "client" {
  datacenters = ["dc1"]

  group "client" {
    task "client" {
      driver = "docker"
      config {
        image = "openjdk:11-jdk"
        args = [
          "bash",
          "-c",
          "(cd /app && java -jar /app/client/build/libs/client-1.0.jar)"
        ]
        network_mode = "nomad"
        extra_hosts = [
          "consul:172.30.0.1",
          "traefik:172.30.0.3",
          "zipkin:172.30.0.4"
        ]
        volumes = [
          "/home/picodotdev/Software/personal/blog-ejemplos/SpringCloudConsulNomadTraefik/:/app"
        ]
      }

      resources {
        cpu    = 200
        memory = 1024
        network {
          mbits = 20
        }
      }
    }
  }
}