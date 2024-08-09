package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountService accountService;

    @Autowired
    public MessageService(MessageRepository repository, AccountService accountService) {
        messageRepository = repository;
        this.accountService = accountService;
    }

    @Bean
    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    
    public Message getMessageByID(int id) {
        return messageRepository.getMessageByID(id);
    }

    @Transactional
    public Message createMessage(Message message) {
        
        if (message.getMessageText().equals("")) {
            throw new InvalidMessageException("Message cannot be blank.");
        }
        if (message.getMessageText().length() > 255) {
            throw new InvalidMessageException("Message cannot be more than 255 characters.");
        }

        Account account = accountService.getAccountById(message.getPostedBy());
        if (account == null) {
            throw new InvalidMessageException("Message must be posted by a real user");
        }
        
        return messageRepository.save(message);
        
    }

    @Transactional
    public Message updateMessage(String messageText) {
        return null;
    }
}
