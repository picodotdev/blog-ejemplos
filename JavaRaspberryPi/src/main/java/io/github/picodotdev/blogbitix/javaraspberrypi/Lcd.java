package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.I2CLcd;
import com.diozero.api.I2CConstants;

import java.nio.ByteOrder;

public class Lcd {

    public static void main(String[] args) throws Exception {
        try (I2CLcd lcd = new I2CLcd(I2CConstants.BUS_0, I2CLcd.DEFAULT_DEVICE_ADDRESS, ByteOrder.LITTLE_ENDIAN, 16, 2)) {
            lcd.setText(0, "Hello World!");
            lcd.createChar(0, I2CLcd.Characters.get("heart"));
            lcd.createChar(1, I2CLcd.Characters.get("smilie"));
            lcd.createChar(2, I2CLcd.Characters.get("space_invader"));
            lcd.setCharacter(13, 0, (char) 0);
            lcd.setCharacter(14, 0, (char) 1);
            lcd.setCharacter(15, 0, (char) 2);
            lcd.setText(1, " powered by Java");
            Thread.sleep(20000);
        }
    }
}
