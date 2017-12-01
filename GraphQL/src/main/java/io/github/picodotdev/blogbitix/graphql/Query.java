package io.github.picodotdev.blogbitix.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.List;

public class Query implements GraphQLQueryResolver {

    private LibraryRepository libraryRepository;

    public Query(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Book> books(BookFilter filter) {
        return libraryRepository.findBooks(filter);
    }

    public Book book(Long id) {
        return libraryRepository.findBookById(id).orElse(null);
    }

    public List<Author> authors() {
        return libraryRepository.getAuthors();
    }

    public Author author(Long id) {
        return libraryRepository.findAuthorById(id).orElse(null);
    }
}
