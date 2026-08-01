import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    application
    pmd
    checkstyle
    id("net.ltgt.errorprone") version "5.1.0"
    id("com.diffplug.spotless") version "7.0.2"
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

pmd {
    toolVersion = "7.26.0"
    threads = 4
    rulesMinimumPriority = 5
    ruleSets = listOf("category/java/bestpractices.xml", "category/java/performance.xml", "category/java/multithreading.xml")
}

checkstyle {
    toolVersion = "13.9.0"
    configFile = file("$rootDir/config/checkstyle.xml")
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

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = false
        html.required = true
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
