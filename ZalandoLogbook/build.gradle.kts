plugins {
  java
  application
  id("org.springframework.boot") version "4.0.5"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.picodotdev.blogbitix"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(platform("com.squareup.okhttp3:okhttp-bom:5.3.2"))
  implementation(platform("org.zalando:logbook-bom:4.0.4"))

  implementation("org.springframework.boot:spring-boot-starter") {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
  }
  implementation("org.springframework.boot:spring-boot-starter-web") {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
  }
  implementation("org.springframework.boot:spring-boot-starter-log4j2")
  implementation("org.apache.logging.log4j:log4j-layout-template-json")

  implementation("com.squareup.okhttp3:okhttp")
  implementation("com.squareup.okhttp3:logging-interceptor")

  implementation("org.zalando:logbook-spring-boot-starter")
  implementation("org.zalando:logbook-okhttp")
}

application {
  mainClass = "io.github.picodotdev.blogbitix.zalandologbook.Main"
}

tasks.withType<Test> {
  useJUnitPlatform()
}
