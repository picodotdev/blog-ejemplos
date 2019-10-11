job "registrator" {
  datacenters = ["dc1"]

  group "registrator" {
    task "registrator" {
      driver = "docker"
      config {
        image = "gliderlabs/registrator:latest"
        args = [
          "consul://host.docker.internal:8500"
        ]
        volumes = [
          "/var/run/docker.sock:/tmp/docker.sock"
        ]
      }

      resources {
        cpu    = 200
        memory = 128
        network {
          mbits = 20
        }
      }
    }
  }
}