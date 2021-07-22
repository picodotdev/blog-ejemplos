package io.github.picodotdev.blogbitix.javarandom;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        System.out.println("Random");
        String randomNumbers = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Integer.toString(RandomUtil.getInt(0, 10)))
                .collect(Collectors.joining(", "));
        System.out.printf("Numbers: %s%n", randomNumbers);

        System.out.println("\nStream");
        String streamNumbers = RandomUtil.getIntStream(1, 10, 10)
                .mapToObj(i -> Integer.toString(i))
                .collect(Collectors.joining(", "));
        System.out.printf("Numbers: %s%n", streamNumbers);

        System.out.println("\nMath");
        String mathNumbers = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Integer.toString(RandomUtil.getIntMath(0, 10)))
                .collect(Collectors.joining(", "));
        System.out.printf("Numbers: %s%n", mathNumbers);

        System.out.println("\nUUID");
        String uuidNumbers = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> RandomUtil.getUUID().toString())
                .collect(Collectors.joining(", "));
        System.out.printf("Numbers: %s%n", uuidNumbers);
    }
}
