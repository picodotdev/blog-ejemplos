package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.LED;

public class PinBlink {

    public static void main(String[] args) throws InterruptedException {
        try (LED led = new LED(18)) {
            for (int i = 0; i < 10; i++) {
                led.on();
                Thread.sleep(2000);
                led.off();
                Thread.sleep(2000);
            }
        }
    }
}
