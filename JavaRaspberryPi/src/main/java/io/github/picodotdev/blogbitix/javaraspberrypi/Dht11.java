package io.github.picodotdev.blogbitix.javaraspberrypi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

public class Dht11 {

    static {
        String architecture = System.getProperty("os.arch");
        String library = String.format("/libdht11-%s.so", architecture);
        try (InputStream is = Dht11.class.getResourceAsStream(library)) {
            Path path = File.createTempFile("libdht11", "so").toPath();
            Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
            System.load(path.toAbsolutePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int gpio;
    private Optional<Integer> temperature;
    private Optional<Integer> humidity;
    private Optional<LocalDateTime> date;

    public Dht11(int gpio) {
        this.gpio = gpio;
        this.temperature = Optional.empty();
        this.humidity = Optional.empty();
        this.date = Optional.empty();

        init();
    }

    private native void init();
    private native void read(int gpio);

    public void update() {
        try {
            for (int i = 0; i < 5; ++i) {
                try {
                    read(gpio);
                    break;
                } catch (Exception e) {
                    Thread.sleep(3000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Integer> getTemperature() {
        return temperature;
    }

    public Optional<Integer> getHumidity() {
        return humidity;
    }

    public Optional<LocalDateTime> getDate() {
        return date;
    }

    public void setTemperatureHumidity(int temperature, int humidity) {
        this.temperature = Optional.of(temperature);
        this.humidity = Optional.of(humidity);
        this.date = Optional.of(LocalDateTime.now());
    }

    public static void main(String[] args) {
        Dht11 sensor = new Dht11(2);
        sensor.update();
        if (sensor.getTemperature().isPresent() && sensor.getHumidity().isPresent()) {
            System.out.printf("Temperature (C): %f, Humidity: %f%n", sensor.getTemperature().get(), sensor.getHumidity().get());
        } else {
            System.out.println("No temperature/humidity");
        }
    }
}
