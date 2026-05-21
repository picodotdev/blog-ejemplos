package io.github.picodotdev.blogbitix.springbootjaxrs;

import java.util.Date;

public class Message {

    private String message;
    private Date date;

    public Message(String message) {
        this.message = message;
        this.date = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}