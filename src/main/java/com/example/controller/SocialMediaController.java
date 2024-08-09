package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountConflictException;
import com.example.exception.AccountRegistrationFailureException;
import com.example.exception.IncorrectLoginException;
import com.example.exception.InvalidMessageException;
import com.example.repository.MessageRepository;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService msgService) {
        this.accountService = accountService;
        messageService = msgService;
    }

    @PostMapping("/register")
    public @ResponseBody Account registrationHandler(@RequestBody Account account) {
        return accountService.createAccount(account); 
    }

    @PostMapping("/login")
    public @ResponseBody Account accountLoginHandler(@RequestBody Account account) {
        Account loggedInAccount = accountService.loginAccount(account);
        if (loggedInAccount == null) {
            throw new IncorrectLoginException("Incorrect username or password.");
        }
        return loggedInAccount;
    }

    @PostMapping("/messages")
    public @ResponseBody Message newMessageHandler(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("/messages")
    public List<Message> allMessageHandler() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public @ResponseBody Message messageHandler(@PathVariable("messageId") int id) {
        return messageService.getMessageByID(id);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public @ResponseBody List<Message> allMessagesByUserHandler(@PathVariable("accountId") int id) {
        List<Message> messageList = messageService.getAllMessages();
        List<Message> messagesByUser = new ArrayList<>();
        for (Message msg : messageList) {
            if (msg.getPostedBy() == id) {
                messagesByUser.add(msg);
            }
        }
        return messagesByUser;
    }

    @ExceptionHandler(IncorrectLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody String handleUnauthorizedLogin(IncorrectLoginException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AccountRegistrationFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleRegistrationFailure(AccountRegistrationFailureException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AccountConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String handleRegistrationConflict(AccountConflictException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidMessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleInvalidMessage(InvalidMessageException e) {
        return e.getMessage();
    }
}
