plugins {
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0") {
        exclude(group = "org.apache.oltu.oauth2", module = "org.apache.oltu.oauth2.common")
    }
    implementation("org.apache.oltu.oauth2:org.apache.oltu.oauth2.client:1.0.1")

    implementation("com.squareup.retrofit2:converter-scalars:2.11.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.11.0")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")

    implementation("com.fasterxml.jackson.core:jackson-core:2.18.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.18.3")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    implementation(project(":client"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "io.github.picodotdev.blogbitix.Main"
}


