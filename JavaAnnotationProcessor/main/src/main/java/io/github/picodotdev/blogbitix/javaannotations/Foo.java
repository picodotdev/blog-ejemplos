package io.github.picodotdev.blogbitix.javaannotations;

import java.util.Optional;

import io.github.picodotdev.blogbitix.annotationprocessor.Builder;
import io.github.picodotdev.blogbitix.annotationprocessor.Value;

@Builder
@Value
public class Foo {

    private String name;
    private Optional<String> color;

    public Foo() {
    }

    public Foo(String name, Optional<String> color) {
        this.name = name;
        this.color = color;
    }

    public void sayHello() throws InterruptedException {
        System.out.println("Hello, my name is " + name + " and my favorite color is " + color.orElse("black"));
    }
}
