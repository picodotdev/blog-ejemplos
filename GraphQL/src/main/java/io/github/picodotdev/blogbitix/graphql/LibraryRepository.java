package io.github.picodotdev.blogbitix.graphql;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class LibraryRepository {

    private long sequence;
    private Collection<Book> books;
    private Collection<Author> authors;

    public LibraryRepository() {
        this.sequence = 0l;
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();

        Author a1 = new Author(nextId(), "Philip K. Dick");
        Author a2 = new Author(nextId(), "George R. R. Martin");
        Author a3 = new Author(nextId(), "Umberto Eco");
        Author a4 = new Author(nextId(), "Andreas Eschbach");
        Author a5 = new Author(nextId(), "Ernest Cline");
        Author a6 = new Author(nextId(), "Anónimo");

        this.authors.addAll(List.of(a1, a2, a3, a4, a5, a6));

        this.books.addAll(
            List.of(
                new Book(nextId(), "Ojo en el cielo", a1),
                new Book(nextId(), "Muerte de la luz", a2),
                new Book(nextId(), "El nombre de la rosa", a3),
                new Book(nextId(), "Los tejedores de cabellos", a4),
                new Book(nextId(), "Ready Player One", a5)
            )
        );
    }

    public Collection<Book> findBooks() {
        return books;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public Optional<Author> findAuthorById(Long id) {
        return authors.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public Book addBook(String title, Long idAuthor, AuthContext context) throws PermissionException, ValidationException {
        if (context.getUser() == null || !context.getUser().equals("admin")) {
            throw new PermissionException("Invalid permissions");
        }
        Optional<Author> author = findAuthorById(idAuthor);
        if (!author.isPresent()) {
            throw new ValidationException("Invalid author");
        }

        Book book = new Book(nextId(), title, author.get());
        books.add(book);
        return book;
    }

    private long nextId() {
        return ++sequence;
    }
}
