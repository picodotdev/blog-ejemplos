import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    application
    id("com.diffplug.spotless") version "7.0.2"
    id("net.ltgt.errorprone") version "5.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jspecify:jspecify:1.0.0")

    errorprone("com.google.errorprone:error_prone_core:2.49.0")
    errorprone("com.uber.nullaway:nullaway:0.13.6")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("io.github.picodotdev.blogbitix.holamundospotless.Main")
}

spotless {
    java {
        target("src/**/*.java")
        targetExclude("**/build/**", "**/generated/**")

        eclipse().configFile("config/picodotdev.xml")
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktlint("1.3.0").setEditorConfigPath(".editorconfig")
    }

    format("misc") {
        target("*.md", "*.yml", ".gitignore", ".editorconfig")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:all", "-Werror"))

    options.errorprone {
        excludedPaths.set(".*/build/generated/.*")
        disable("StringCaseLocaleUsage")
        error("DefaultCharset", "MissingOverride", "UnusedVariable")

        check("NullAway", CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "io.github.picodotdev.blogbitix")
    }
}

tasks.named("check") {
    dependsOn("spotlessCheck")
}
