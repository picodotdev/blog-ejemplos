# Hola Mundo

Aplicación Java ejecutable con `./gradlew run`, configurada con:

- **Gradle Kotlin DSL** (`build.gradle.kts`, `settings.gradle.kts`) + Gradle Wrapper
- **Java 21** (toolchain de Gradle)
- **Spotless** con formateo a partir de un fichero exportado de **IntelliJ**
- **Error Prone** para análisis estático en tiempo de compilación
- **EditorConfig** para coherencia entre editores
- **JUnit 5** para pruebas

## Estructura del proyecto

```
hola-mundo/
├── build.gradle.kts                  ← configuración del build
├── settings.gradle.kts               ← nombre del proyecto raíz
├── gradle.properties                 ← opciones de la JVM de Gradle
├── gradlew                           ← wrapper para Linux/macOS
├── gradlew.bat                       ← wrapper para Windows
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.properties
│       └── gradle-wrapper.jar        ← (ver paso 1 más abajo)
├── config/
│   └── intellij-java-google-style.xml   ← estilo exportado de IntelliJ
├── .editorconfig
├── .gitignore
└── src/
    ├── main/java/com/example/holamundo/
    │   ├── HolaMundoApplication.java   ← clase principal (main)
    │   └── Greeter.java
    └── test/java/com/example/holamundo/
        └── GreeterTest.java
```

## Cómo ejecutar la aplicación

### Paso 1 — Completar el Gradle Wrapper

El proyecto incluye `gradlew`, `gradlew.bat` y `gradle-wrapper.properties`,
pero **falta `gradle/wrapper/gradle-wrapper.jar`** (no se puede empaquetar
un binario desde este entorno). Hay dos formas equivalentes de obtenerlo:

**Opción A — si tienes Gradle instalado** (con SDKMAN, Homebrew, scoop, etc.):

```bash
cd hola-mundo
gradle wrapper --gradle-version 8.10.2
```

Esto regenera el wrapper completo (jar incluido) en `gradle/wrapper/`.

**Opción B — descarga directa del jar oficial:**

```bash
cd hola-mundo
curl -L -o gradle/wrapper/gradle-wrapper.jar \
  https://raw.githubusercontent.com/gradle/gradle/v8.10.2/gradle/wrapper/gradle-wrapper.jar
```

### Paso 2 — Ejecutar

```bash
./gradlew run
```

Salida esperada:

```
> Task :run
Hola, Mundo!
```

### Otras tareas útiles

```bash
./gradlew test             # ejecuta los tests JUnit 5
./gradlew spotlessCheck    # verifica el formato del código
./gradlew spotlessApply    # aplica el formato automáticamente
./gradlew build            # compila + tests + spotlessCheck + Error Prone
./gradlew installDist      # genera scripts de arranque en build/install/
./gradlew tasks            # lista todas las tareas disponibles
```

## Plugin `application`

El bloque que hace posible `./gradlew run` es este, en `build.gradle.kts`:

```kotlin
plugins {
    application
    // ...
}

application {
    mainClass.set("com.example.holamundo.HolaMundoApplication")
}
```

El plugin `application` aporta las tareas `run`, `installDist`, `distZip` y
`distTar`, todas listas para empaquetar la aplicación.

## Exportar el estilo desde IntelliJ

El fichero `config/intellij-java-google-style.xml` incluido es una plantilla
basada en Google Java Style. Para usar el estilo real de tu equipo:

1. En IntelliJ: `Settings` → `Editor` → `Code Style` → `Java`.
2. Engranaje → **Export… → IntelliJ IDEA code style XML**.
3. Reemplaza `config/intellij-java-google-style.xml` con el fichero exportado.

Spotless consume ese XML con el formatter de Eclipse, compatible con el
formato exportado por IntelliJ.
