package io.github.picodotdev.blogbitix.microprofile;

import javax.json.bind.annotation.JsonbAnnotation;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import java.util.UUID;
import java.time.LocalDateTime;;

public class Quote {

    private UUID id;
    private LocalDateTime date;
    private String text;

    public Quote(String text) {
        this.id = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.text = text;
    }

    public UUID getUuid() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}