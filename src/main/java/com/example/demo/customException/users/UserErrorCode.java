package com.example.demo.customException.users;

import org.springframework.http.HttpStatus;

import com.example.demo.customException.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter     // interface의 메소드가 get 메소드만 있기 때문에 굳이 재정의 하지 않기 위해 사용
@RequiredArgsConstructor    // 상수가 생성될 때 HttpStatus, code, message 필드 값을 받는 생성자를 생성
public enum UserErrorCode implements ErrorCode {
    // 상수
    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, 404, "존재하지 않는 사용자입니다."),
    USER_ALREADY_EXIST_ERROR(HttpStatus.CONFLICT, 409, "이미 존재하는 이름입니다."),
    ;
    
    // 필드값
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
