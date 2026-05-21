package io.github.picodotdev.blogbitix.javaheappollution;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {

    private List<String> strings;

    @Before
    public void before() {
        strings = new ArrayList<>();
        strings.add("Hello World!");
    }

    @Test(expected = ClassCastException.class)
    public void genericsRaw() {
        List objects = strings;

        objects.add(42); // unchecked warning, heap pollution in strings

        for (String string : strings) {
            System.out.println(string); // ClassCastException is thrown
        }
    }

    @Test(expected = ClassCastException.class)
    public void genericsRawNumber() {
        List objects = strings;
        List<Number> numbers = objects; // unchecked warning

        numbers.add(42); // heap pollution in strings

        for (String string : strings) {
            System.out.println(string); // ClassCastException is thrown
        }
    }

    @Test
    public void warningVarargs() {
        add(strings, "Hello", "World!");
    }

    @Test(expected = ClassCastException.class)
    public void varargs() {
        List<String>[] array = new List[] { strings, strings };

        addFaulty(strings, array);

        for (List<String> a : array) {
            for (String s : a) {
                System.out.println(s); // ClassCastException is thrown
            }
        }
    }

    @SafeVarargs
    private final <T> void add(List<T> list, T... elements) {
        for (T x : elements) {
            list.add(x);
        }
    }

    private <T> void addFaulty(List<T> list, List<T>... elements) { // possible heap polltion warning
        Object[] array = elements; // valid, no warning

        for (List<T> x : elements) {
            list.addAll(x);
        }

        array[0] = Arrays.asList(42); // heap pollution
    }
}