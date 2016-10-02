package io.github.picodotdev.blogbitix.holamundobytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // Create a Class
        DynamicType.Unloaded<?> unloaded = new ByteBuddy()
                .subclass(Object.class)
                .name("io.github.picodotdev.blobitix.holamundobytebuddy.Object")
                .method(named("toString")).intercept(FixedValue.value("Hello World!"))
                .make();

        Class<?> objectClass = unloaded.load(Main.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Object object = objectClass.newInstance();
        System.out.printf("%s: %s\n", object.getClass().getName(), object.toString());

        // Intercept methods
        Foo foo = new ByteBuddy()
                .subclass(Foo.class)
                .method(isDeclaredBy(Foo.class)).intercept(FixedValue.value("One!"))
                .method(named("foo")).intercept(FixedValue.value("Two!"))
                .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("Three!"))
                .make()
                .load(Main.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded()
                .newInstance();

        System.out.println();
        System.out.printf("%s.bar(): %s\n", foo.getClass().getName(), foo.bar());
        System.out.printf("%s.foo(): %s\n", foo.getClass().getName(), foo.foo());
        System.out.printf("%s.foo(Object o): %s\n", foo.getClass().getName(), foo.foo("¡Hello World!"));

        // Delegating a method call
        Class<? extends Source> sourceClass = new ByteBuddy()
                .subclass(Source.class)
                .method(named("hello")).intercept(MethodDelegation.to(Target.class))
                .make()
                .load(Main.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        String message = sourceClass.newInstance().hello("World");
        System.out.println();
        System.out.println(message);
    }
}