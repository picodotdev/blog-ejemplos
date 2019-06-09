package io.github.picodotdev.blogbitix.graphql.repository;

import io.github.picodotdev.blogbitix.graphql.misc.AuthContext;
import io.github.picodotdev.blogbitix.graphql.misc.PermissionException;
import io.github.picodotdev.blogbitix.graphql.misc.ValidationException;
import io.github.picodotdev.blogbitix.graphql.type.Author;
import io.github.picodotdev.blogbitix.graphql.type.Book;
import io.github.picodotdev.blogbitix.graphql.type.BookFilter;
import io.github.picodotdev.blogbitix.graphql.type.Comment;
import io.github.picodotdev.blogbitix.graphql.type.Magazine;
import io.github.picodotdev.blogbitix.graphql.type.Publication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class LibraryRepository {

    private long sequence;
    private List<Book> books;
    private List<Comment> comments;
    private List<Author> authors;
    private List<Magazine> magazines;

    public LibraryRepository() {
        this.sequence = 0l;
        this.books = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.magazines = new ArrayList<>();

        Author a1 = new Author(nextId(), "Philip K. Dick");
        Author a2 = new Author(nextId(), "George R. R. Martin");
        Author a3 = new Author(nextId(), "Umberto Eco");
        Author a4 = new Author(nextId(), "Andreas Eschbach");
        Author a5 = new Author(nextId(), "Ernest Cline");
        Author a6 = new Author(nextId(), "Anónimo");

        this.authors.addAll(List.of(a1, a2, a3, a4, a5, a6));

        LongStream.range(1, 10).forEach(i -> this.comments.add(new Comment(i,"Comment " + i)));

        this.books.addAll(
            List.of(
                new Book(nextId(), "Ojo en el cielo", a1, LocalDate.of(1957, 1, 1), this.comments),
                new Book(nextId(), "Muerte de la luz", a2, LocalDate.of(1977, 1, 1), this.comments),
                new Book(nextId(), "El nombre de la rosa", a3, LocalDate.of(1980, 1, 1), this.comments),
                new Book(nextId(), "Los tejedores de cabellos", a4, LocalDate.of(1995, 1, 1), this.comments),
                new Book(nextId(), "Ready Player One", a5, LocalDate.of(2011, 1, 1), this.comments)
            )
        );

        this.magazines.addAll(
            List.of(
                new Magazine(nextId(), "Muy interesante", 65L),
                new Magazine(nextId(), "PC Actual", 90L)
            )
        );
    }

    public Book findBook(Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Book> findBooks(BookFilter filter) {
        Stream<Book> stream = books.stream();
        if (filter != null) {
            stream = stream.filter(b -> b.getTitle().matches(filter.getTitle()));
        }
        return stream.collect(Collectors.toList());
    }

    public List<Publication> findPublications() {
        List<Publication> publications = new ArrayList<>();
        publications.addAll(books);
        publications.addAll(magazines);
        return publications;
    }

    public Optional<Book> findBookById(Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    public List<Comment> findComments(Long idBook, Long idAfter, Long limit) {
        Book book = findBook(idBook);
        Stream<Comment> stream = book.getComments().stream();
        if (idAfter != null) {
            stream = stream.dropWhile(b -> idAfter != null && !b.getId().equals(idAfter)).skip(1);
        }
        if (limit != null) {
            stream = stream.limit(limit);
        }
        return stream.collect(Collectors.toList());
    }

    public List<Author> getAuthors() {
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

        Book book = new Book(nextId(), title, author.get(), LocalDate.now(), Collections.EMPTY_LIST);
        books.add(book);
        return book;
    }

    private long nextId() {
        return ++sequence;
    }
}
