package io.github.picodotdev.blogbitix.javaapplication;

import java.io.InputStream;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = App.class.getResourceAsStream("/version.properties");

        Properties properties = new Properties();
        properties.load(inputStream);

        System.out.println(properties.getProperty("app.version.formatted-string"));

    }
}
