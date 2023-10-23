package com.example.demo.repositoryTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.demo.repository.RepositorySample;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class UsersRepositoryTests {
    
    @Autowired
    private RepositorySample repository;

    @Test
    public void getListTest(){

        Pageable pageable = PageRequest.of(0, 10);

        log.info("---------------------------- list");
        log.info(repository.getList(pageable));
        log.info("----------------------------");

    }

}
