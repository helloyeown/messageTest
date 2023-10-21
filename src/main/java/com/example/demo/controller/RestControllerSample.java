package com.example.demo.controller;

import com.example.demo.model.dto.*;
import com.example.demo.service.ServiceSample;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestControllerSample {

    private final ServiceSample service;

    @PostMapping
    public CreateResponse create(@RequestBody CreateRequest request) {
        return service.create(request);
    }

    @GetMapping
    public GetResponse getById(Long id) {
        return service.getSample(id);
    }

    @GetMapping("/page")
    public List<GetResponse> getPage(@PageableDefault() Pageable pageRequest) {
        return service.getPage(pageRequest);
    }

    @PutMapping("/{id}")
    public UpdateResponse update(@PathVariable String id, @RequestBody UpdateRequest request) {
        if(!StringUtils.isNumeric(id)) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        return service.update(Long.valueOf(id), request);
    }
}
