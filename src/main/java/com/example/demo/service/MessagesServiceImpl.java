package com.example.demo.service;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.model.dto.messages.CreateMessage;
import com.example.demo.model.dto.messages.CreateResponse;
import com.example.demo.model.dto.messages.GetMessages;
import com.example.demo.model.entity.EntitySample;
import com.example.demo.model.entity.Message;
import com.example.demo.repository.RepositorySample;
import com.example.demo.repository.messages.MessagesRepository;
import java.util.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessagesServiceImpl implements MessagesService {
    
    private final MessagesRepository repository;
    private final RepositorySample sample;

    // 쪽지 생성
    @Override
    @Transactional
    public CreateResponse create(CreateMessage request){
        Message message = new Message();

        Optional<EntitySample> result = sample.findByName(request.getSender());
        EntitySample sender = result.orElseThrow();

        Optional<EntitySample> result2 = sample.findByName(request.getReceiver());
        EntitySample receiver = result2.orElseThrow(); 

        message.setText(request.getText());
        message.setSender(sender);
        message.setReceiver(receiver);

        return new CreateResponse(repository.save(message).getId());
    }

    // 쪽지 읽기
    @Override
    @Transactional
    public GetMessages readMessage(Long id){

        Optional<Message> result = repository.findById(id);
        Message message = result.orElseThrow();

        // entity -> dto 형변환 (필드가 여러 개여서 modelMapper 사용)
        ModelMapper modelMapper = new ModelMapper();

        // EntitySample를 String으로 변환하는 Converter 정의
        Converter<EntitySample, String> toName = new AbstractConverter<EntitySample,String>() {
            protected String convert(EntitySample source){
                return source.getName();
            }
        };

        // Converter를 ModelMapper에 등록
        modelMapper.addConverter(toName);

        // Message Entity를 GetMessages DTO로 변환
        GetMessages dto = modelMapper.map(message, GetMessages.class);

        return dto;

    }

    // 쪽지 목록
    @Override
    public List<GetMessages> getMessagesList(Pageable pageRequest) {

        return repository.getList(pageRequest);

    }

    // 쪽지 삭제
    @Override
    public void removeMessage(Long id) {

        Optional<Message> result = repository.findById(id);
        Message message = result.orElseThrow();

        message.setDelFlag(true);
        repository.save(message);

    }

}