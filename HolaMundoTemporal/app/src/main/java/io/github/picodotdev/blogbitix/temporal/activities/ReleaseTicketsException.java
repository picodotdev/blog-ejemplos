package io.github.picodotdev.blogbitix.temporal.activities;

public class ReleaseTicketsException extends RuntimeException {

    public ReleaseTicketsException() {
    }

    public ReleaseTicketsException(String message) {
        super(message);
    }

    public ReleaseTicketsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReleaseTicketsException(Throwable cause) {
        super(cause);
    }
}
