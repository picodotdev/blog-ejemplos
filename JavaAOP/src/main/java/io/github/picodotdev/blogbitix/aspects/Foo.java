package io.github.picodotdev.blogbitix.aspects;

import java.util.Random;

public class Foo implements IFoo {

    public void echo() {
        System.out.println("echo");
    }

    public int sum(int a, int b) {
        return a + b;
    }

    public void sleep() {
        try {
            long time = new Random().nextInt(1500);
            Thread.sleep(time);
        } catch(Exception e) {}
    }
}