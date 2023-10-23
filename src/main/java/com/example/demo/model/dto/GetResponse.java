package com.example.demo.model.dto;

import com.example.demo.model.entity.EntitySample;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class GetResponse {

    private String name;

    public GetResponse(String name) {
        this.name = name;
    }
}
