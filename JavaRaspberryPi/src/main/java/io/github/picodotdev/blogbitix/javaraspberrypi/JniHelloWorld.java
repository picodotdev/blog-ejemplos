package io.github.picodotdev.blogbitix.javaraspberrypi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class JniHelloWorld {

    static {
        String architecture = System.getProperty("os.arch");
        String library = String.format("/libjnihelloworld-%s.so", architecture);
        try (InputStream is = JniHelloWorld.class.getResourceAsStream(library)) {
            Path path = File.createTempFile("libjnihelloworld", "so").toPath();
            Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
            System.load(path.toAbsolutePath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private native void print();

    public static void main(String[] args) {
        new JniHelloWorld().print();
    }
}
