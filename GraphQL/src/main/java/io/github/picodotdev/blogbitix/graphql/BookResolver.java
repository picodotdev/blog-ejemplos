package io.github.picodotdev.blogbitix.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;

import java.util.UUID;

public class BookResolver implements GraphQLResolver<Book> {

    public BookResolver() {
    }

    public String getIsbn(Book book) {
        return UUID.randomUUID().toString();
    }
}
