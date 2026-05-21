package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.Button;
import com.diozero.Buzzer;
import com.diozero.LED;
import com.diozero.api.GpioPullUpDown;

public class LedButtonBuzzer {

    public static void main(String[] args) throws Exception {
        try (LED led = new LED(18); Buzzer buzzer = new Buzzer(21, true); Button button = new Button(22, GpioPullUpDown.PULL_UP)) {
            led.on();
            Thread.sleep(3000);
            led.off();

            button.whenPressed(() -> {
                led.on();
                buzzer.on();
            });
            button.whenReleased(() -> {
                led.off();
                buzzer.off();
            });
            Thread.sleep(30000);
        }
    }
}
