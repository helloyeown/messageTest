package com.example.demo.model.dto;

import com.example.demo.model.entity.EntitySample;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetResponse {

    private String name;

    public GetResponse(String name) {
        this.name = name;
    }
}
