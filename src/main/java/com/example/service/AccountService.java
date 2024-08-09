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

    /**
     * Returns all accounts
     * @return a list of Account objects
     */
    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    /**
     * Attempts to log into an account using a username and password.
     * @param account account object containing login information
     * @return the account on success, null if not.
     */
    public Account loginAccount(Account account) {
        return accountRepository.loginAccount(account.getUsername(), account.getPassword());
    }

    /**
     * Attempts to create a new account. Account creation will fail if username is blank, 
     * password is less than 4 characters, or username already exists
     * @param newAccount new Account to create
     * @return Newly-created account object, or null if unsuccessful
     */
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

    /**
     * Gets an account by its id
     * @param id id of account
     * @return Account object on success, null if not
     */
    public Account getAccountById (int id) {
        return accountRepository.getAccountById(id);
    }
}


