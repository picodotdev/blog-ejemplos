package io.github.picodotdev.blogbitix.javasort;

import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        int[] array = new int[] { 64, 47, 33, 82, 91, 1, 45 };
        List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());
        List<Person> persons = List.of(
                new Person("Juan", 56, LocalDate.of(1982, 3, 26)),
                new Person("María", 24, LocalDate.of(2018, 8, 7)),
                new Person("Marisa", 63, LocalDate.of(2021, 4, 17)),
                new Person("Antonio", 41, LocalDate.of(2020, 5, 2))
        );

        Comparator<Person> nameComparator = new NameComparator();
        Comparator<Person> ageComparator = Comparator.comparing(Person::getAge);
        Comparator<Person> hireComparator = Comparator.comparing(Person::getHired);

        {
            System.out.println("Array (unsorted)");
            Arrays.stream(array).forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("List (unsorted)");
            list.stream().forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("Persons (unsorted)");
            persons.stream().forEach(i -> System.out.println(i));
        }

        {
            System.out.println();
            System.out.println("Array (sorted)");
            int[] arraySorted = Arrays.copyOf(array, array.length);
            Arrays.sort(arraySorted);
            Arrays.stream(arraySorted).forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("List (sorted)");
            List<Integer> listSorted = new ArrayList<>(list);
            Collections.sort(listSorted);
            listSorted.stream().forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("Persons (sorted, natural)");
            List<Person> personsSortedNatural = new ArrayList<>(persons);
            Collections.sort(personsSortedNatural);
            personsSortedNatural.stream().forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("Persons (sorted, name)");
            List<Person> personsSortedName = new ArrayList<>(persons);
            Collections.sort(personsSortedName, nameComparator);
            personsSortedName.stream().forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("Persons (sorted, age)");
            List<Person> personsSortedAge = new ArrayList<>(persons);
            Collections.sort(personsSortedAge, ageComparator);
            personsSortedAge.stream().forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("Persons (sorted, hired)");
            List<Person> personsSortedHired = new ArrayList<>(persons);
            Collections.sort(personsSortedHired, hireComparator);
            personsSortedHired.stream().forEach(i -> System.out.println(i));
        }

        {
            System.out.println();
            System.out.println("Array (sorted descending)");
            Integer[] arraySorted = Arrays.stream(array).boxed().toArray(Integer[]::new);
            Arrays.sort(arraySorted, Collections.reverseOrder());
            Arrays.stream(arraySorted).forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("List (sorted descending)");
            List<Integer> listSortedNatural = new ArrayList<>(list);
            Collections.sort(listSortedNatural, Comparator.<Integer>naturalOrder().reversed());
            listSortedNatural.stream().forEach(i -> System.out.println(i));

            System.out.println();
            System.out.println("Persons (sorted descending, age)");
            List<Person> personsSortedAge = new ArrayList<>(persons);
            Collections.sort(personsSortedAge, ageComparator.reversed());
            personsSortedAge.stream().forEach(i -> System.out.println(i));
        }

        {
            List<Integer> integers = randomList(25);
            List<Integer> bubbleSort = new BubbleSort<Integer>().sort(integers, Integer::compareTo);
            List<Integer> mergeSort = new MergeSort<Integer>().sort(integers, Integer::compareTo);
            List<Integer> quickSort = new QuickSort<Integer>().sort(integers, Integer::compareTo);
            System.out.printf("Integers (%s):    %s%n", integers.size(), integers.stream().map(i -> i.toString()).collect(Collectors.joining(",")));
            System.out.printf("Bubble Sort (%s): %s%n", bubbleSort.size(), bubbleSort.stream().map(i -> i.toString()).collect(Collectors.joining(",")));
            System.out.printf("Merge Sort (%s):  %s%n", mergeSort.size(), mergeSort.stream().map(i -> i.toString()).collect(Collectors.joining(",")));
            System.out.printf("Quick Sort (%s):  %s%n", quickSort.size(), quickSort.stream().map(i -> i.toString()).collect(Collectors.joining(",")));
        }
    }

    private static class Person implements Comparable<Person> {

        private String name;
        private int age;
        private LocalDate hired;

        public Person(String name, int age, LocalDate hired) {
            this.name = name;
            this.age = age;
            this.hired = hired;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public LocalDate getHired() {
            return hired;
        }

        @Override
        public int compareTo(Person o) {
            if (age < o.getAge()) {
                return -1;
            } else if (age > o.getAge()) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return String.format("Person(name=%s, age=%s, hired=%s)", name, age, hired.format(DateTimeFormatter.ISO_DATE));
        }
    }

    private static class NameComparator implements Comparator<Person> {

        private Collator collator;

        public NameComparator() {
            this.collator = Collator.getInstance(new Locale("es"));
            collator.setStrength(Collator.TERTIARY);
        }

        @Override
        public int compare(Person o1, Person o2) {
            return collator.compare(o1.getName(), o2.getName());
        }
    }

    public static List<Integer> randomList(int elements) {
        return new Random().ints(elements, 0, 100).boxed().collect(Collectors.toList());
    }
}
