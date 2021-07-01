package io.github.picodotdev.blogbitix.javaswagger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Message {

    private Long id;
    private String text;

    public Message(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @JsonIgnore
    public boolean isBlank() {
        return text == null || text.isBlank();
    }
}
