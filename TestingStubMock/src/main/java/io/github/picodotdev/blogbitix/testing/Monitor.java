package io.github.picodotdev.blogbitix.testing;

import java.util.ArrayList;
import java.util.List;

public class Monitor {

    private Sensor sensor;
    private Alarm alarm;
    private int maxTemperature;

    public List<Integer> measurings;

    public Monitor(Sensor sensor, Alarm alarm, int maxTemperature) {
        this.sensor = sensor;
        this.alarm = alarm;
        this.maxTemperature = maxTemperature;

        this.measurings = new ArrayList<>();
    }

    void check() {
        int temperature = sensor.getTemperature();
        if (temperature <= maxTemperature) {
            measurings.clear();
        } else {
            measurings.add(temperature);
        }
        if (measurings.size() >= 3) {
            alarm.fire();
        }
    }
}