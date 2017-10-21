#!/usr/bin/env bash
javac -d build/mods/helloworld src/helloworld/module-info.java src/helloworld/io/github/picodotdev/blogbitix/java9/helloworld/Main.java
mkdir -p build/jars/
jar --create -f build/jars/helloworld.jar --main-class io.github.picodotdev.blogbitix.java9.helloworld.Main -C build/mods/helloworld .
java --module-path build/jars/ --module helloworld

