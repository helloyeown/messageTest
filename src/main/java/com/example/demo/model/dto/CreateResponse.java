package com.example.demo.model.dto;

import lombok.Getter;

@Getter
public class CreateResponse {

    private long id;

    public CreateResponse(long id) {
        this.id = id;
    }
}
