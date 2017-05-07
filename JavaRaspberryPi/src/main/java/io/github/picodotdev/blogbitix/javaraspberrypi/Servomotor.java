package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.LED;
import com.diozero.sandpit.Servo;

public class Servomotor {

    public static void main(String[] args) throws InterruptedException {
        try (LED led = new LED(21); Servo servo = new Servo(18, 50, 1.45F)) {
            led.on();

            for (int i = 0; i < 5; ++i) {
                Thread.sleep(3000);
                servo.setPulseWidthMs(0.6F);
                Thread.sleep(3000);
                servo.setPulseWidthMs(2.3F);
            }

            servo.setPulseWidthMs(1.45F);
            Thread.sleep(2000);
        }
    }
}
