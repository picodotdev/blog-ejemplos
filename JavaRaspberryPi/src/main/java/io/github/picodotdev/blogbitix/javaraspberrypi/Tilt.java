package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.LED;
import com.diozero.api.*;

public class Tilt {

    public static void main(String[] args) throws InterruptedException {
        try (LED led = new LED(18); DigitalInputDevice tilt = new DigitalInputDevice(21)) {
            led.on();
            Thread.sleep(2000);
            led.off();

            tilt.whenActivated(() -> {
                led.on();
            });
            tilt.whenDeactivated(() ->  {
                led.off();
            });

            Thread.sleep(30000);
        }
    }
}
