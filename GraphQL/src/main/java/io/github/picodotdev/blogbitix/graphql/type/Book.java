package io.github.picodotdev.blogbitix.graphql.type;

import java.time.LocalDate;
import java.util.List;

public class Book extends Publication {
    
    private Long id;
    private String title;
    private Author author;
    private LocalDate date;
    private List<Comment> comments;

    public Book(Long id, String title, Author author, LocalDate date, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.date = date;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}