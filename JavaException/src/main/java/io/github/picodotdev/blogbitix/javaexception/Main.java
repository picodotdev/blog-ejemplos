package io.github.picodotdev.blogbitix.javaexception;

import io.vavr.control.Either;

import java.util.Optional;

public class Main {

    private static int errorCode(boolean error) {
        return (error) ? 1 : 0;
    }

    private static Optional<Integer> optional(boolean error) {
        return (error) ? Optional.empty() : Optional.of(0);
    }

    private static void exception(boolean error) throws Exception {
        if (error) throw new Exception();
    }

    private static Either<Exception, Integer> either(boolean error) {
        return (error) ? Either.left(new Exception()) : Either.right(1);
    }

    public static void main(String[] args) {
        int errorCode = errorCode(true);
        if (errorCode != 0) {
            System.out.printf("Error code: %d%n", errorCode);
        }

        Optional<Integer> value = optional(true);
        if (!value.isPresent()) {
            System.out.println("Optional empty");
        }

        try {
            exception(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Either<Exception, Integer> either = either(true);
        if (either.isLeft()) {
            System.out.printf("Either exception: %s%n", either.left().getClass().getClass().getName());
        }
    }
}
