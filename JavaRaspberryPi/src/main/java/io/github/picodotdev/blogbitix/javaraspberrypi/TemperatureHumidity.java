package io.github.picodotdev.blogbitix.javaraspberrypi;

import com.diozero.I2CLcd;
import com.diozero.api.I2CConstants;

import java.nio.ByteOrder;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TemperatureHumidity {

    public static void main(String[] args) throws Exception {
        Dht11 sensor = new Dht11(2);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        try (I2CLcd lcd = new I2CLcd(I2CConstants.BUS_0, I2CLcd.DEFAULT_DEVICE_ADDRESS, ByteOrder.LITTLE_ENDIAN, 16, 2)) {
            Runnable monitor = new Runnable() {
                @Override
                public void run() {
                    try {
                        sensor.update();

                        System.out.printf("Temperature: %dºC, Humidity: %d%%, Date: %s%n", sensor.getTemperature().get(), sensor.getHumidity().get(), sensor.getDate().get().format(DateTimeFormatter.ISO_DATE_TIME));

                        if (sensor.getTemperature().isPresent() && sensor.getHumidity().isPresent() && sensor.getDate().isPresent()) {
                            lcd.setText(0, String.format("T: %dC, H: %d%% ", sensor.getTemperature().get(), sensor.getHumidity().get()));
                            lcd.setText(1, String.format("%s", sensor.getDate().get().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                        } else {
                            lcd.setText(0, String.format("No data"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            service.scheduleAtFixedRate(monitor, 1, 5, TimeUnit.SECONDS);
            Thread.sleep(60000);
        } finally {
            service.shutdown();
        }
    }
}
