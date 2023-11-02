package com.example.demo.ServiceTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.dto.messages.CreateMessage;
import com.example.demo.service.MessagesService;
import com.example.demo.service.MessagesServiceImpl;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MessagesServiceTests {
    
    @Autowired
    private MessagesService service;

    // 쪽지 생성
    @Test
    public void insertTest(){

        CreateMessage request = CreateMessage.builder()
            .text("insert service test")
            .sender("Jo")
            .receiver("Goo").build();

        service.create(request);

        log.info("success");

    }

    // 쪽지 읽기
    @Test
    public void readTest(){

        log.info(service.readMessage(50L));

    }

    // 쪽지 목록
    @Test
    public void getListTest(){

        Pageable pageable = PageRequest.of(0, 10);

        log.info(service.getMessagesList(pageable));

    }

    // 쪽지 삭제
    @Test
    public void deleteMessageTest(){

        service.removeMessage(2L);

    }

}
