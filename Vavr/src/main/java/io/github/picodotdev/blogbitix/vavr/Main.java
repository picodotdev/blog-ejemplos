package io.github.picodotdev.blogbitix.vavr;

import io.vavr.*;
import io.vavr.Lazy;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.math.BigDecimal;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

public class Main {
    public static void main(String[] args) throws Throwable {
        System.out.println("# Side-Effects");
        {
            CheckedFunction2<Integer, Integer, Integer> divide = (Integer dividend, Integer divisor) -> dividend / divisor;
            Try.of(() -> divide.apply(4, 2)).onSuccess(System.out::println).onFailure(e -> e.printStackTrace());
            Try.of(() -> divide.apply(4, 0)).onSuccess(System.out::println).onFailure(e -> e.printStackTrace());
        }

        System.out.println("");
        System.out.println("# Functional Data Structures");
        {
            List.of(0, 2, 3).tail().prepend(1).map(o -> o * 2).forEach(System.out::println);
        }

        System.out.println("");
        System.out.println("# Collections");
        {
            Stream<Integer> list = List.of(0, 2, 3).tail().prepend(1).toStream();
            for (Integer i : list) {
                System.out.println(i);
            }
        }

        System.out.println("");
        System.out.println("# Tuples");
        {
            Tuple3<String, Integer, BigDecimal> tuple = new Tuple3<>("El señor de los anillos", 1272, new BigDecimal("10.40"));
            System.out.printf("Titulo: %s, Páginas: %d, Precio: %.2f%n", tuple._1, tuple._2, tuple._3);
        }

        System.out.println("");
        System.out.println("# Values");
        {
            Option.of("foo");

            Lazy<Double> lazy = Lazy.of(Math::random);
            System.out.println(lazy.get());
            System.out.println(lazy.get());

            CheckedFunction2<Integer, Integer, Integer> divide = (Integer dividend, Integer divisor) -> dividend / divisor;
            Either<Throwable, Integer> value1 = Try.of(() -> divide.apply(4, 2)).toEither();
            Either<Throwable, Integer> value2 = Try.of(() -> divide.apply(4, 0)).toEither();

            value1.right().peek(System.out::println);
            value2.left().peek(e -> e.printStackTrace());

            CheckedFunction1<Integer, Integer> curriedDivide = divide.curried().apply(10);
            System.out.println(curriedDivide.apply(2));

            Function0<Double> hashCache = Function0.of(Math::random).memoized();

            System.out.println(hashCache.apply());
            System.out.println(hashCache.apply());

            Number number = new Double(4.34);
            Option<Number> plusOne = Match(number).option(
                Case($(instanceOf(Integer.class)), i -> i + 1),
                Case($(instanceOf(Double.class)), d -> d + 2)
            );
            System.out.println(plusOne.get());
        }
    }
}
