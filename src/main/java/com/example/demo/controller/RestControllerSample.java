package com.example.demo.controller;

import com.example.demo.model.dto.*;
import com.example.demo.service.ServiceSample;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/{id}")
    public GetResponse getById(@PathVariable Long id) {
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

        log.info("Controller.................................");
        log.info(request);
        log.info(id);

        // id가 숫자가 아니라면 예외 처리 -> 세밀한 예외 처리를 위해 id를 String 타입으로 받음
        if(!StringUtils.isNumeric(id)) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        return service.update(Long.valueOf(id), request);
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        if(!StringUtils.isNumeric(id)) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        
        service.deleteUser(Long.valueOf(id));
    }

    // 회원 정보 인증
    @GetMapping("/manage/check")
    public ResponseEntity check() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
