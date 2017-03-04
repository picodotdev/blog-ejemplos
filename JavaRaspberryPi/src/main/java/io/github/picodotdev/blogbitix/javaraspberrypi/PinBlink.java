package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.LED;

public class PinBlink {

    public static void main(String[] args) throws InterruptedException {
        try (LED led = new LED(18)) {
            led.on();
            System.out.println("on");
            Thread.sleep(5000);
            led.off();
            System.out.println("off");
            Thread.sleep(2000);
            led.on();
            System.out.println("on");
            Thread.sleep(2000);
            led.off();
            System.out.println("off");
            Thread.sleep(2000);
            led.on();
            System.out.println("on");
            Thread.sleep(2000);
        }
    }
}
