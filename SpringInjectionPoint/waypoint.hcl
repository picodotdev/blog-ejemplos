project = "Waypoint"
app "spring-injection-point" {
  build {
    use "pack" {
      builder = "paketobuildpacks/builder:base"
      buildpacks = ["gcr.io/paketo-buildpacks/ca-certificates", "gcr.io/paketo-buildpacks/syft", "gcr.io/paketo-buildpacks/procfile", "gcr.io/paketo-buildpacks/adoptium", "gcr.io/paketo-buildpacks/gradle", "gcr.io/paketo-buildpacks/executable-jar", "gcr.io/paketo-buildpacks/spring-boot"]
      process_type = "app"
      static_environment = {
        BP_JVM_TYPE = "JRE"
        BP_JVM_VERSION = "11"
        BP_GRADLE_BUILT_ARTIFACT = "app/build/distributions/app.zip"
      }
    }
  }
  deploy {
    use "docker" {
      service_port = 8080
    }
  }
}
