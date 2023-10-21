package com.example.demo.model.dto;

import com.example.demo.model.entity.EntitySample;
import lombok.Getter;

@Getter
public class UpdateResponse {

    private String resultYN;

    private long id;

    public UpdateResponse(EntitySample entity){
        this.id = entity.getId();
        this.resultYN = "Y";
    }
}
