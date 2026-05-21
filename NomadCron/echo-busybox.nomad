job "echo-busybox" {
    datacenters = ["dc1"]
    type = "batch"

    periodic {
        cron = "*/1 * * * * *"
        prohibit_overlap = false
    }

    group "echo" {
        task "echo" {
            driver = "docker"
            config {
	        image = "busybox:latest"
                command = "/bin/echo"
                args    = ["Hello Word! (busybox)"]
            }
        }
    }
}
