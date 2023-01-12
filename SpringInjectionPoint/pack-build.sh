#!/usr/bin/env bash

pack build spring-injection-point \
  --env "BP_JVM_TYPE=JDK" \
  --env "BP_JVM_VERSION=11" \
  --env "BP_GRADLE_BUILT_ARTIFACT=app/build/distributions/app.zip" \
  --builder paketobuildpacks/builder-jammy-base \
  --buildpack gcr.io/paketo-buildpacks/ca-certificates \
  --buildpack gcr.io/paketo-buildpacks/syft \
  --buildpack gcr.io/paketo-buildpacks/procfile \
  --buildpack gcr.io/paketo-buildpacks/adoptium \
  --buildpack gcr.io/paketo-buildpacks/gradle \
  --buildpack gcr.io/paketo-buildpacks/executable-jar \
  --buildpack gcr.io/paketo-buildpacks/spring-boot \
  --default-process app \
  --path .