package io.github.picodotdev.blogbitix.graphql.resolver;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;
import io.github.picodotdev.blogbitix.graphql.misc.DefaultGraphqlContext;
import io.github.picodotdev.blogbitix.graphql.repository.LibraryRepository;
import io.github.picodotdev.blogbitix.graphql.type.Author;
import io.github.picodotdev.blogbitix.graphql.type.Book;
import io.github.picodotdev.blogbitix.graphql.type.BookFilter;
import io.github.picodotdev.blogbitix.graphql.type.CommentsConnection;
import io.github.picodotdev.blogbitix.graphql.type.Publication;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Query implements GraphQLQueryResolver {

    private LibraryRepository libraryRepository;

    public Query(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Book> books(BookFilter filter, DataFetchingEnvironment environment) throws InterruptedException  {
        List<Book> books = libraryRepository.findBooks(filter);
        DefaultGraphqlContext context = environment.getContext();

        SelectedField batchedIsbn = getField(environment, "batchedIsbn");
        if (Objects.nonNull(batchedIsbn)) {
            System.out.printf("Getting %d ISBNs...", books.size());
            Thread.sleep(3000);
            System.out.printf("ok%n");
            Map<Long, String> isbns = books.stream().map(b -> b.getId()).collect(Collectors.toMap(
                Function.identity(),
                v -> UUID.randomUUID().toString()
            ));;
            context.getData().put("batchedIsbn", isbns);
        }

        SelectedField batchedComments = getField(environment, "batchedComments");
        if (Objects.nonNull(batchedComments)) {
            Thread.sleep(3000);
            String after = (String) batchedComments.getArguments().get("after");
            Long limit = (Long) batchedComments.getArguments().get("limit");
            BookResolver resolver = new BookResolver(libraryRepository);
            Map<Long, CommentsConnection> commentsConnections = books.stream().collect(Collectors.toMap(
                k -> k.getId(),
                v -> resolver.getComments(v, after, limit)
            ));
            context.getData().put("batchedComments", commentsConnections);
        }
        return books;
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

    private SelectedField getField(DataFetchingEnvironment environment, String name) {
        return environment.getSelectionSet().getField(name);
    }
}
