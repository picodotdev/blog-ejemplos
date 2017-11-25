package io.github.picodotdev.blogbitix.graphql;

import java.util.List;

public class Book {
    
    private Long id;
    private String title;
    private Author author;
    private List<Comment> comments;

    public Book(Long id, String title, Author author, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}