#!/usr/bin/env bash
javac -d build/mods/helloworld src/helloworld/module-info.java src/helloworld/io/github/picodotdev/blogbitix/java9/helloworld/Main.java
java --module-path build/mods --module helloworld/io.github.picodotdev.blogbitix.java9.helloworld.Main
