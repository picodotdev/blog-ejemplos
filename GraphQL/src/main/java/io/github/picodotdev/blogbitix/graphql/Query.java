package io.github.picodotdev.blogbitix.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.Collection;

public class Query implements GraphQLQueryResolver {
    
    private LibraryRepository libraryRepository;

    public Query(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Collection<Book> books() {
        return libraryRepository.findBooks();
    }

    public Collection<Author> authors() {
        return libraryRepository.getAuthors();
    }

    public Author author(Long id) {
        return libraryRepository.findAuthorById(id).orElse(null);
    }
}
