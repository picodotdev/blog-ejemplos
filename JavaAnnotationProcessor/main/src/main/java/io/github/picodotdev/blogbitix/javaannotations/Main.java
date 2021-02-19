package io.github.picodotdev.blogbitix.javaannotations;

import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hola mundo");
        Foo foo = new FooBuilder().name("foo").color(Optional.of("red")).build();
        foo.sayHello();
    }
}
