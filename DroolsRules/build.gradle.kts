plugins {
    application
    alias(libs.plugins.springboot)
}

project.apply {
  group = "io.github.picodotdev.blogbitix.drools"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

configurations.implementation {
  exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

dependencies {
    implementation(platform(libs.springBootBom))
    implementation(platform(libs.droolsBom))
    implementation(platform(libs.kogitoBom))

    implementation(libs.bundles.springBoot)
    implementation(libs.bundles.drools)
}

application {
    mainClass = "io.github.picodotdev.blogbitix.drools.Main"
}
