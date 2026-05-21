import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import java.security.MessageDigest
import java.util.HexFormat
import org.gradle.internal.extensions.stdlib.capitalized

plugins {
    id("java-library")
    id("org.openapi.generator") version "7.12.0"
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
}

sourceSets {
    main {
        java {
            srcDir("${projectDir}/build/generate/openapi/src/main/java")
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
    File("${project.layout.projectDirectory}/src/main/openapi").walk().filter({ it -> it.isFile() }).forEach { it ->
        val name = it.name.removeSuffix(".yaml")
        dependsOn("generate${name.capitalized()}Client")
    }
}

File("${project.layout.projectDirectory}/src/main/openapi").walk().filter({ it -> it.isFile() }).forEach { it ->
    val name = it.name.removeSuffix(".yaml")
    tasks.register<GenerateTask>("generate${name.capitalized()}Client") {
        generatorName.set("java")
        library.set("retrofit2")
        apiNameSuffix.set("Client")
        modelNamePrefix.set("Client")
        inputSpec.set("${project.layout.projectDirectory}/src/main/openapi/${name}.yaml")
        outputDir.set("${project.layout.projectDirectory}/build/generate/openapi")
        apiPackage.set("io.github.picodotdev.${name}.client.api")
        modelPackage.set("io.github.picodotdev.${name}.client.model")
        invokerPackage.set("io.github.picodotdev.${name}.client.invoker")
        configOptions.put("dateLibrary", "java8")
        configOptions.put("useJakartaEe", "true")
        additionalProperties.put("serializationLibrary", "jackson")

        onlyIf {
            val file = File("${project.layout.projectDirectory.asFile}/src/main/openapi/${name}.yaml")
            val hashFile = File("${project.layout.buildDirectory.get().asFile}/tmp/${name}.yaml.hash")
            !hashMatches(file, hashFile)
        }
    }
}

fun hashMatches(file: File, hashFile: File): Boolean {
    val fileContent = file.readText()
    val fileContentHash = if (!hashFile.exists()) {
        null
    } else {
        hashFile.readText()
    }
    val hash = hashString(fileContent)
    hashFile.parentFile.mkdirs()
    hashFile.writeText(hash)
    return hash == fileContentHash
}

fun hashString(data: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(data.toByteArray())
    return HexFormat.of().formatHex(digest)
}
