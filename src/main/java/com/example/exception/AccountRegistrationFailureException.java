package com.example.exception;

import com.example.entity.Account;

public class AccountRegistrationFailureException extends RuntimeException{
    public AccountRegistrationFailureException(String message) {
        super(message);
    }   
}
