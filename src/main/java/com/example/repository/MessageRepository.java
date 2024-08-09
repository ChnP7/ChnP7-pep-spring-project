package com.example.repository;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

    @Query("FROM Message")
    public List<Message> getAllMessages();

    @Query("FROM Message WHERE messageId = :id")
    public Message getMessageByID(@Param("id") int id);
}
