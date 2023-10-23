package com.example.demo.controller;

import com.example.demo.model.dto.*;
import com.example.demo.service.ServiceSample;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/users")
public class RestControllerSample {

    private final ServiceSample service;

    // 회원 생성
    @PostMapping("/")
    public CreateResponse create(@RequestBody CreateRequest request) {

        log.info("create....................");

        return service.create(request);
    }

    // id를 통해 회원 가져오기
    @GetMapping
    public GetResponse getById(Long id) {
        return service.getSample(id);
    }

    // 회원 목록
    @GetMapping("/page")
    public List<GetResponse> getPage(@PageableDefault() Pageable pageRequest) {
        return service.getPage(pageRequest);
    }

    // 회원 이름 수정
    @PutMapping("/{id}")
    public UpdateResponse update(@PathVariable String id, @RequestBody UpdateRequest request) {
        if(!StringUtils.isNumeric(id)) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        return service.update(Long.valueOf(id), request);
    }
}
