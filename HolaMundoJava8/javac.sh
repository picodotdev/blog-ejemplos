#!/usr/bin/env bash
javac -classpath "libraries/*" -sourcepath src/main/java -source 1.8 -target 1.8 -d target/classes src/main/java/io/github/picodotdev/blogbitix/java8/helloworld/Main.java
cp -r src/main/resources/* target/classes