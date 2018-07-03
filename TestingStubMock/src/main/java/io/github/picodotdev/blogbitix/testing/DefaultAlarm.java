package io.github.picodotdev.blogbitix.testing;

public class DefaultAlarm implements Alarm {

    @Override
    public void fire() {
        System.out.println("Alarm!");
    }
}