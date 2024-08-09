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

    /**
     * Returns all messages
     * @return A list of all messages
     */
    @Bean
    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    /**
     * Gets a specific message by its id
     * @param id id of message to obtain
     * @return A message object, or null if not found.
     */
    public Message getMessageByID(int id) {
        return messageRepository.getMessageByID(id);
    }

    /**
     * Attempts to create a new message. A message must not be empty, less than 255 characters,
     * and must have a valid account as the postedBy.
     * @param message the new message to add.
     * @return A message object representing the newly-created message
     */
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

    /**
     * Updates a message's text
     * @param id id of message to update
     * @param messageText new text to update the message to
     * @return The amount of rows affected
     */
    @Transactional
    public int updateMessage(int id, String messageText) {
        System.out.println("OKAY: " + messageText);
        if (messageText.equals("")) {
            System.out.println("Yes");
            throw new InvalidMessageException("Message cannot be empty.");
        }
        if (messageText.length() > 255) {
            throw new InvalidMessageException("Message cannot be more than 255 characters");
        }
        Message message = getMessageByID(id);
        if (message == null) {
            throw new InvalidMessageException("Cannot update a message that does not exist");
        }
        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1;
    }

    /**
     * Deleted a message
     * @param id id of message to delete
     * @return number of messages deleted on success, or null if not found.
     */
    @Transactional
    public Integer deleteMessage(int id) {
        Message message = getMessageByID(id);
        if (message != null) {
            messageRepository.delete(message);
            return 1;
        }
        return null;
    }
}
