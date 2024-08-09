package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    
    @Query("FROM Account")
    public List<Account> getAllAccounts();

    @Query("From Account WHERE username = :username AND password = :password")
    public Account loginAccount(@Param("username") String username, @Param("password") String password);

    @Query("From Account WHERE accountId = :id")
    public Account getAccountById(@Param("id") int id);

}
