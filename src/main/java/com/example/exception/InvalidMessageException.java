package com.example.exception;

/**
 * Represents an exception thrown when a message is attempted to be created or modified
 * with invalid information, such as an empty message or a message having a non-existing
 * user.
 */
public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException(String message) {
        super(message);
    }
}
