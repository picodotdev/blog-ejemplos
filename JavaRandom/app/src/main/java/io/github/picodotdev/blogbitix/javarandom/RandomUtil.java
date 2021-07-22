package io.github.picodotdev.blogbitix.javarandom;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class RandomUtil {

    public static int getInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static IntStream getIntStream(int min, int max) {
       return new Random().ints(min, max + 1);
    }

   public static IntStream getIntStream(int min, int max, int size) {
       return new Random().ints(size, min, max + 1);
    }

    public static int getIntMath(int min, int max) {
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    public static UUID getUUID() {
        return UUID.randomUUID();
    }
}
