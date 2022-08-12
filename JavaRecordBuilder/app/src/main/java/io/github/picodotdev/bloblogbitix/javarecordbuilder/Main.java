package io.github.picodotdev.bloblogbitix.javarecordbuilder;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> authors = List.of("Pedro Igor Silva", "Stian Thorgersen");
        Book book = BookBuilder.builder()
                .title("Keycloak - Identity and Access Management for Modern Applications ")
                .authors(authors)
                .isbn("978-1800562493")
                .year(2021)
                .pages(362)
                .build();

        System.out.println(book);
    }
}
