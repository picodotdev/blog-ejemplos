plugins {
    id("application")
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

repositories {
    mavenCentral()
}

configurations.all {
  exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("io.cloudevents:cloudevents-core:3.0.0")
    implementation("io.cloudevents:cloudevents-kafka:3.0.0")
    implementation("io.cloudevents:cloudevents-json-jackson:3.0.0")
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

application {
  mainClass = "io.github.picodotdev.blogbitix.asyncapicloudevents.Main"
}
