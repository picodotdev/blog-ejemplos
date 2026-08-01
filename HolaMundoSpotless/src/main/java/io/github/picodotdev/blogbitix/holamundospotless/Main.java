package io.github.picodotdev.blogbitix.holamundospotless;

public class Main {

    @SuppressWarnings({ "SelfAssignment", "PMD.SystemPrintln" })
    public static void main(final String[] args) {
        Greeter greeter = new Greeter();
        greeter = greeter;
        System.out.println(greeter.greet("Mundo"));
        // Test JSpecify, Error Prone and NullAway
        //System.out.println(greeter.greet(null));
    }
}
