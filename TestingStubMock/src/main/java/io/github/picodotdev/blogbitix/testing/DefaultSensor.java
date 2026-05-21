package io.github.picodotdev.blogbitix.testing;

import java.util.Random;

public class DefaultSensor implements Sensor {

    @Override
    public int getTemperature() {
        return new Random().nextInt(50);
    }
}