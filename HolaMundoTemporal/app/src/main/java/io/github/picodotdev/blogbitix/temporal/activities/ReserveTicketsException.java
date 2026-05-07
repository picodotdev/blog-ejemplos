package io.github.picodotdev.blogbitix.temporal.activities;

public class ReserveTicketsException extends RuntimeException {

    public ReserveTicketsException() {
    }

    public ReserveTicketsException(String message) {
        super(message);
    }

    public ReserveTicketsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReserveTicketsException(Throwable cause) {
        super(cause);
    }
}
