package com.example.exception;

/**
 * Represents an exception thrown when a user attempts to log in with incorrect
 * credentials.
 */
public class IncorrectLoginException extends RuntimeException {
    public IncorrectLoginException(String message) {
        super(message);
    }
}
