package io.github.picodotdev.blogbitix.javaforeach;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        System.out.println("for i");
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }

        System.out.println("foreach");
        for (int i : Arrays.asList(0, 1, 2, 3, 4)) {
            System.out.println(i);
        }

        System.out.println("for counter");
        for (int i : new Counter(0, 5)) {
            System.out.println(i);
        }

        System.out.println("stream foreach");
        IntStream.range(0, 5).forEach(i -> {
            System.out.println(i);
        });

        System.out.println("Sentencia break");
        for (int i = 0; i < 5; ++i) {
             if (i == 3) {
                break;
            }
            System.out.println(i);
        }

        System.out.println("Sentencia continue");
        for (int i = 0; i < 5; ++i) {
            if (i == 3) {
                continue;
            }
            System.out.println(i);
        }
    }
}
