#!/usr/bin/env bash
javac -d build src/helloworld/module-info.java src/helloworld/io/github/picodotdev/blogbitix/java9/helloworld/Main.java
java --module-path build --module helloworld/io.github.picodotdev.blogbitix.java9.helloworld.Main
