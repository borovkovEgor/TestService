package com.borovkov.egor.testservice.exception;

public class InvalidSubscriptionException extends RuntimeException {
    public InvalidSubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSubscriptionException(String message) {super(message);}
}
