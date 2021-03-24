package io.github.picodotdev.blogbitix.nexus.application;

import io.github.picodotdev.blogbitix.nexus.library.Library;

public class Main {

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println("Main: " + new Main().getGreeting());
        System.out.println("Library: " + new Library().someLibraryMethod());
    }
}
