package com.example.demo.repositoryTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.example.demo.model.dto.messages.GetMessages;
import com.example.demo.model.entity.EntitySample;
import com.example.demo.model.entity.Message;
import com.example.demo.repository.RepositorySample;
import com.example.demo.repository.messages.MessagesRepository;

import lombok.extern.log4j.Log4j2;
import java.util.*;

import javax.transaction.Transactional;

@SpringBootTest
@Log4j2
public class MessagesRepositoryTests {
    
    @Autowired
    private MessagesRepository repository;

    @Autowired
    private RepositorySample sample;

    // 메시지 목록 가져오기
    @Test
    @Transactional
    @Rollback(false)
    public void getListTest(){

        Pageable pageable = PageRequest.of(0, 10);

        List<GetMessages> messageList = repository.getList(pageable);

        // 각 GetMessages 객체의 필드 값 출력
        for (GetMessages message : messageList) {
            System.out.println("ID: " + message.getId());
            System.out.println("Text: " + message.getText());
            System.out.println("Sended Date Time: " + message.getSendedDt());
            System.out.println("Sender: " + message.getSender());
            System.out.println("Receiver: " + message.getReceiver());
            System.out.println("Is Read: " + message.isRead());
            System.out.println("-----------------------");
        }

    }

    // 쪽지 번호로 조회
    @Test
    @Transactional
    public void getOneTest(){

        Optional<Message> result = repository.findById(1L);
        Message message = result.orElseThrow();

        log.info(message);

    }

    // 메시지 데이터 등록
    @Test
    public void insertMessagesTest(){

        Optional<EntitySample> result = sample.findById(1L);
        EntitySample sender = result.orElseThrow();

        Optional<EntitySample> result2 = sample.findById(2L);
        EntitySample receiver = result2.orElseThrow();

        for(int i=0; i<50; i++){
            Message message = Message.builder()
                .text("test text" + i)
                .isRead(false)
                .sender(sender)
                .receiver(receiver).build();
    
            repository.save(message);
        }

    }

    // 쪽지 삭제 (논리적 삭제)
    @Test
    public void deleteTest(){

        Optional<Message> result = repository.findById(50L);
        Message message = result.orElseThrow();

        message.setDelFlag(true);
        repository.save(message);

        log.info("success");

    }

    @Test
    public void modifyTest(){

        Optional<Message> result = repository.findById(50L);
        Message message = result.orElseThrow();
        
        message.setText("modified");

        repository.save(message);

        log.info("success");

    }

}
