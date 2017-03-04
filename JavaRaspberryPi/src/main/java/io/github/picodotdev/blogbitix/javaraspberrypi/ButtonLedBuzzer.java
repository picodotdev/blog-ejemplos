package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.Button;
import com.diozero.Buzzer;
import com.diozero.LED;
import com.diozero.api.GpioPullUpDown;

public class ButtonLedBuzzer {

    public static void main(String[] args) throws Exception {
        try (LED led = new LED(21); Buzzer buzzer = new Buzzer(23, true); Button button = new Button(22, GpioPullUpDown.PULL_UP)) {
            led.on();
            buzzer.on();
            Thread.sleep(3000);
            led.off();
            buzzer.off();

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
