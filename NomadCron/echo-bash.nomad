job "echo-bash" {
    datacenters = ["dc1"]
    type = "batch"

    periodic {
        cron = "*/1 * * * * *"
        prohibit_overlap = false
    }

    group "echo" {
        task "echo" {
            driver = "raw_exec"
            config {
                command = "/bin/echo"
                args    = ["Hello Word! (bash)"]
            }
        }
    }
}
