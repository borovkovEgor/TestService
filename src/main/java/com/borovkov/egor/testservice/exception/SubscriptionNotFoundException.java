package com.borovkov.egor.testservice.exception;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
