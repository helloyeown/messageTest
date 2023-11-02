package com.example.demo.repository.messages;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.Message;

public interface MessagesRepository extends JpaRepository<Message, Long> {
}
