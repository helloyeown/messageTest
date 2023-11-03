package com.example.demo.model.dto.messages;

import com.example.demo.model.entity.Message;

import lombok.Getter;

@Getter
public class UpdateResponse {
    
    private String resultYN;

    private long id;

    public UpdateResponse(Message message){
        this.id = message.getId();
        this.resultYN = "Y";
    }

}
