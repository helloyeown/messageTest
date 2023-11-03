package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.messages.CreateMessage;
import com.example.demo.model.dto.messages.CreateResponse;
import com.example.demo.model.dto.messages.GetMessages;
import com.example.demo.model.dto.messages.UpdateRequest;
import com.example.demo.model.dto.messages.UpdateResponse;
import com.example.demo.service.MessagesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Log4j2
public class MessageController {
    
    private final MessagesService service;

    // 쪽지 목록
    @GetMapping
    public List<GetMessages> getList(Pageable pageRequest) {
        log.info("controller................. ");
    
        return service.getMessagesList(pageRequest);
    }

    // 쪽지 읽기
    @GetMapping("/{id}")
    public GetMessages readMessages(@PathVariable Long id) {
        return service.readMessage(id);
    }

    // 쪽지 생성
    @PostMapping
    public CreateResponse sendMessages(@RequestBody CreateMessage request) {
        return service.create(request);
    }

    // 쪽지 삭제 (논리적 삭제)
    @DeleteMapping("/{id}")
    public void deleteMessages(@PathVariable Long id) {
        service.removeMessage(id);
    }

    // 쪽지 내용 수정
    @PutMapping("/modify/{id}")
    public UpdateResponse modifyMessages(@PathVariable Long id, @RequestBody UpdateRequest request) {
        return service.modifyMessage(id, request);
    }

    // 읽음 체크
    @PutMapping("/{id}")
    public void isRead(@PathVariable Long id) {
        service.isRead(id);
    }
}
