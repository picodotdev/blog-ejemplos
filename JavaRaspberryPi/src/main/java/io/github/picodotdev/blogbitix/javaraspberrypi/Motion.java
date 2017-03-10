package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.LED;
import com.diozero.sandpit.MotionSensor;

public class Motion {

    public static void main(String[] args) throws InterruptedException {
        try (LED led = new LED(18); MotionSensor motion = new MotionSensor(21)) {
            led.on();
            Thread.sleep(2000);

            motion.whenActivated(() -> {
                System.out.println("on");
                led.on();
            });
            motion.whenDeactivated(() ->  {
                System.out.println("off");
                led.off();
            });

            Thread.sleep(60000);
        }
    }
}
