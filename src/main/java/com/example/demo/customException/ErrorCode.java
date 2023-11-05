package com.example.demo.customException;

import org.springframework.http.HttpStatus;

// Client에게 전달할 에러 코드
public interface ErrorCode {
    
    String name();
    HttpStatus getHttpStatus();
    int getCode();
    String getMessage();

}
