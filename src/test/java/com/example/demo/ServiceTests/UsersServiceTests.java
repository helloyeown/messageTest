package com.example.demo.ServiceTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.dto.CreateRequest;
import com.example.demo.model.dto.UpdateRequest;
import com.example.demo.service.ServiceSample;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class UsersServiceTests {
    
    @Autowired
    private ServiceSample service;

    // 회원 가입
    @Test
    public void insertUserTest(){

        CreateRequest user = new CreateRequest("Jo");

        service.create(user);

        log.info("success");

    }

    // id로 회원 이름 불러오기
    @Test
    public void getSample(){

        log.info(service.getSample(1L));

    }

    // 회원 목록 with paging
    @Test
    public void getUserList(){

        Pageable pageable = PageRequest.of(0, 10);

        log.info(service.getPage(pageable));

    }

    @Test
    public void updateUserName(){

        UpdateRequest updateName = new UpdateRequest("Yeye");

        service.update(1L, updateName);

        log.info("success");

    }

}
