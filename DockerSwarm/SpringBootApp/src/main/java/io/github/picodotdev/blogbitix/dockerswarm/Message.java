package io.github.picodotdev.blogbitix.dockerswarm;

import java.time.LocalDateTime;

public class Message {

    private LocalDateTime dateTime;
    private String message;

    public Message() {
        this.dateTime = LocalDateTime.now();
    }

    public Message(String message) {
        this();
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
