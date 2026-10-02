plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.jacksonDependencies))

    implementation(libs.bundles.jackson)
    implementation(libs.bundles.jakarta)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "io.github.picodotdev.blogbitix.javajsonread.Main"
}

