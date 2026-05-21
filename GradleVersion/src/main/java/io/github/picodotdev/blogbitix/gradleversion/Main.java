package io.github.picodotdev.blogbitix.gradleversion;

import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        InputStream is = Main.class.getResourceAsStream("/build-info.properties");
        if (is != null) {
            properties.load(is);
        }
        String version = properties.getProperty("version");
        String timestamp = properties.getProperty("timestamp");

        System.out.printf("Versión: %s%n", version);
        System.out.printf("Timestamp: %s%n", timestamp);
    }
}
