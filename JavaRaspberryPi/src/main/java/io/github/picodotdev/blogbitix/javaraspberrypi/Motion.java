package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.LED;
import com.diozero.sandpit.MotionSensor;

public class Motion {

    public static void main(String[] args) throws InterruptedException {
        try (LED led = new LED(18); MotionSensor motion = new MotionSensor(21)) {
            led.on();
            Thread.sleep(2000);
            led.off();

            motion.whenActivated(() -> {
                led.on();
            });
            motion.whenDeactivated(() ->  {
                led.off();
            });

            Thread.sleep(30000);
        }
    }
}
