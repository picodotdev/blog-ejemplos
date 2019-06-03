package io.github.picodotdev.blogbitix.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import io.github.picodotdev.blogbitix.graphql.LibraryRepository;
import io.github.picodotdev.blogbitix.graphql.type.Author;
import io.github.picodotdev.blogbitix.graphql.type.Book;
import io.github.picodotdev.blogbitix.graphql.type.BookFilter;
import io.github.picodotdev.blogbitix.graphql.type.Publication;

import java.util.List;

public class Query implements GraphQLQueryResolver {

    private LibraryRepository libraryRepository;

    public Query(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Book> books(BookFilter filter) {
        return libraryRepository.findBooks(filter);
    }

    public List<Publication> publications() {
        return libraryRepository.findPublications();
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
