package com.example.exception;

/**
 * Represents an exception thrown when attempting to create an account with information
 * that conflicts with an existing account, such as a username.
 */
public class AccountConflictException extends RuntimeException {
    public AccountConflictException(String message) {
        super(message);
    }
}
