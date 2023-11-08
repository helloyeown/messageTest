package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.example.demo.model.dto.messages.GetMessages;
import com.example.demo.model.entity.Message;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    // Entity -> DTO
    @Mapping(source = "sender.name", target = "sender")
    @Mapping(source = "receiver.name", target = "receiver")
    public GetMessages toDTO(Message message);
    
}
