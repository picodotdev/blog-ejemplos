plugins {
    application
    alias(libs.plugins.spring.boot)
}

repositories {
    mavenCentral()
}

configurations.all {
  exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

dependencies {
    implementation(platform(libs.spring.boot.dependencies))

    implementation(libs.bundles.spring.boot)
    implementation(libs.temporal)

    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "io.github.picodotdev.blogbitix.temporal.Main"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
