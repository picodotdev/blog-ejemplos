package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.LED;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

public class ChristmasTree {

    public static void main(String[] args) throws Exception {
        int[] pins = new int[] { 17, 18, 21, 22, 23, 24, 25, 4, 7, 8, 11, 9, 10, 15, 14 };
        List<LED> leds = new ArrayList<>(pins.length);

        try {
            for (int pin : pins) {
                leds.add(new LED(pin));
            }
            for (LED led : leds) {
                led.setOn(true);
            }
            Thread.sleep(5000);

            long[] times = new Random().longs(30,1500, 2501).toArray();
            Random status = new Random();
            for (long time : times) {
                for (LED led : leds) {
                    led.setOn(status.nextBoolean());
                }
                Thread.sleep(time);
            };
        } finally {
            for (LED led : leds) {
                led.close();
            }
        }
    }
}
