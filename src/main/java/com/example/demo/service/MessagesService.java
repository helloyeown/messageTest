package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.dto.messages.CreateMessage;
import com.example.demo.model.dto.messages.CreateResponse;
import com.example.demo.model.dto.messages.GetMessages;
import com.example.demo.model.dto.messages.UpdateRequest;
import com.example.demo.model.dto.messages.UpdateResponse;

@Transactional
public interface MessagesService {
    
    CreateResponse create(CreateMessage request);

    GetMessages readMessage(Long id);

    List<GetMessages> getMessagesList(Pageable pageRequest);

    void removeMessage(Long id);

    UpdateResponse modifyMessage(Long id, UpdateRequest request);

    void isRead(Long id);

}
