package io.github.picodotdev.blogbitix.temporal.activities;

public class PaymentAuthorizationException extends RuntimeException {

    public PaymentAuthorizationException() {
    }

    public PaymentAuthorizationException(String message) {
        super(message);
    }

    public PaymentAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentAuthorizationException(Throwable cause) {
        super(cause);
    }
}
