job "app-birds" {
  datacenters = ["dc1"]

  group "frontend" {
    network {
      mode = "bridge"

      port "http" {
        static = 21000
        to     = 21000
      }
      port "envoy_metrics" {
        static = 20200
        to = 20200
      }
    }

    service {
      name = "frontend"
      port = 6060

      meta {
        prometheus_port = "20200"
      }

      connect {
        sidecar_service {
          port = 21000

          proxy {
            upstreams {
              destination_name = "backend"
              local_bind_port = 6001
            }
            config {
              envoy_prometheus_bind_addr = "0.0.0.0:20200"
            }
          }
        }
      }
    }
    
    task "frontend" {
      driver = "docker"

      env {
        BIND_ADDR="0.0.0.0:6060"
        BACKEND_URL="http://localhost:6001"
        TRACING_URL="http://192.168.56.10:9411"
      }

      resources {
        cpu    = 350
        memory = 1024
      }

      config {
        image = "ghcr.io/consul-up/birdwatcher-frontend:1.0.0"
        memory_hard_limit = "1024"
      }
    }
  }


  group "backend" {
    network {
      mode = "bridge"

      port "envoy_metrics" {
        static = 20201
        to = 20201
      }
    }

    service {
      name = "backend"
      port = 7000
    
      meta {
        prometheus_port = "20201"
        version = "v1"
      }

      connect {
        sidecar_service {
          port = 22000
          proxy {
            config {
              envoy_prometheus_bind_addr = "0.0.0.0:20201"
            }
          }
        }
      }
    }
    
    task "backend" {
      driver = "docker"

      env {
        BIND_ADDR="0.0.0.0:7000"
        TRACING_URL="http://192.168.56.10:9411"
      }

      resources {
        cpu    = 350
        memory = 1024
      }

      config {
        image = "ghcr.io/consul-up/birdwatcher-backend:1.0.0"
        memory_hard_limit = "1024"
      }
    }
  }
  
  group "backend-v2" {
    network {
      mode = "bridge"

      port "envoy_metrics" {
        static = 20202
        to = 20202
      }
    }

    service {
      name = "backend"
      port = 7001
    
      meta {
        prometheus_port = "20202"
        version = "v2"
      }

      connect {
        sidecar_service {
          port = 22001
          proxy {
            config {
              envoy_prometheus_bind_addr = "0.0.0.0:20202"
            }
          }
        }
      }
    }

    task "backend" {
      driver = "docker"

      env {
        BIND_ADDR="0.0.0.0:7001"
        TRACING_URL="http://192.168.56.10:9411"
        VERSION="v2"
      }

      resources {
        cpu    = 350
        memory = 1024
      }

      config {
        image = "ghcr.io/consul-up/birdwatcher-backend:1.0.0"
        memory_hard_limit = "1024"
      }
    }
  }
}

