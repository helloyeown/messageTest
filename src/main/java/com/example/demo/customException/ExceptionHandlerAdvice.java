package com.example.demo.customException;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.customException.common.CommonErrorCode;

import lombok.extern.slf4j.Slf4j;


// 기존 예외 처리에서 ControllerAdvice를 이용한 로깅 방식으로 변경

@RestControllerAdvice
@Slf4j      // 로거 객체 생성 어노테이션
public class ExceptionHandlerAdvice {

    // 모든 에러 -> 하위에서 에러 못 받았을 때
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(Exception e) {
        // NestedExceptionUtils.getMostSpecificCause() -> 가장 구체적인 원인, 즉 가장 근본 원인을 찾아서 반환
        log.error("[Exception] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorCode code = CommonErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.of(code.getHttpStatus(), code.getCode(), code.getMessage());

        return ResponseEntity.status(code.getHttpStatus()).body(response);
    }

}
