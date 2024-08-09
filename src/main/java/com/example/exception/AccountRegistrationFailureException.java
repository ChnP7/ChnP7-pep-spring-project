package com.example.exception;

import com.example.entity.Account;

/**
 * Represents an exception thrown when an account is attempted to made that does
 * not fulfill certain requirements, such as having a password that is less than 
 * a certain amount of characters long.
 */
public class AccountRegistrationFailureException extends RuntimeException{
    public AccountRegistrationFailureException(String message) {
        super(message);
    }   
}
