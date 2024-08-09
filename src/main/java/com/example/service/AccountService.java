package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.exception.AccountConflictException;
import com.example.exception.AccountRegistrationFailureException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public Account loginAccount(Account account) {
        return accountRepository.loginAccount(account.getUsername(), account.getPassword());
    }

    @Transactional
    public Account createAccount(Account newAccount) {

        if(newAccount.getUsername().equals("")) {
            throw new AccountRegistrationFailureException("Username cannot be blank");
        }
        if (newAccount.getPassword().length() < 4) {
            throw new AccountRegistrationFailureException("Password must be at least 4 characters");
        }

        List<Account> accountList = getAllAccounts();
        for (Account account : accountList) {
            if (account.getUsername().equals(newAccount.getUsername())) {
                throw new AccountConflictException("An account with this username already exists");
            }
        }
        
        return accountRepository.save(newAccount);
    }
}


