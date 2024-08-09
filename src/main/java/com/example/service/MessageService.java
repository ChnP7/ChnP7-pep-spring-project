package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository repository) {
        messageRepository = repository;
    }

    @Bean
    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    
    public Message getMessageByID(int id) {
        return messageRepository.getMessageByID(id);
    }

    public Message createMessage(Message message) {
        
        if (message.getMessageText().equals("")) {
            throw new InvalidMessageException("Message cannot be blank.");
        }
        if (message.getMessageText().length() > 255) {
            throw new InvalidMessageException("Message cannot be more than 255 characters.");
        }

        return null;
        
    }
}
