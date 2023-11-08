package com.example.demo.customException;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.customException.common.CommonErrorCode;
import com.example.demo.customException.users.UserErrorCode;

import lombok.extern.slf4j.Slf4j;


// 기존 예외 처리에서 ControllerAdvice를 이용한 로깅 방식으로 변경
@RestControllerAdvice
@Slf4j      // 로거 객체 생성 어노테이션
public class ExceptionHandlerAdvice {

    // Common
    // 모든 에러 -> 하위에서 에러 못 받았을 때
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        // NestedExceptionUtils.getMostSpecificCause() -> 가장 구체적인 원인, 즉 가장 근본 원인을 찾아서 반환
        log.error("[Exception] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorCode code = CommonErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.of(code.getHttpStatus(), code.getCode(), code.getMessage());

        return ResponseEntity.status(code.getHttpStatus()).body(response);
    }

    // 메소드가 잘못되었거나 부적합한 인수를 전달했을 경우 -> 필수 파라미터 없을 때
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity HandleIllegalArgumentException(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorCode code = CommonErrorCode.ILLEGAL_ARGUMENT_ERROR;
        ErrorResponse response = ErrorResponse.of(code.getHttpStatus(), code.getCode(),
            String.format("%s %s", code.getMessage(), NestedExceptionUtils.getMostSpecificCause(e)));
        
        return ResponseEntity.status(code.getHttpStatus()).body(response);
    }

    //잘못된 포맷 요청  ex)Json으로 안 보냈을 때
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("[HttpMessageNotReadableException] cause: {}, message: {}", e);
        ErrorCode code = CommonErrorCode.INVALID_FORMAT_ERROR;
        ErrorResponse response = ErrorResponse.of(code.getHttpStatus(), code.getCode(), code.getMessage());

        return ResponseEntity.status(code.getHttpStatus()).body(response);
    }

    // Users
    // 중복 회원 예외 처리 (Custom Exception)
    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity handleDuplicateMemberException(DuplicateMemberException e) {
        log.error("[DuplicateMemberException: Conflict] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorCode code = UserErrorCode.USER_ALREADY_EXIST_ERROR;
        ErrorResponse response = ErrorResponse.of(code.getHttpStatus(), code.getCode(), code.getMessage());

        return ResponseEntity.status(code.getHttpStatus()).body(response);
    }

    // 해당 멤버가 없을 때
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("[EntityNotFoundException] cause: {}, message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        ErrorCode code = UserErrorCode.USER_NOT_FOUND_ERROR;
        ErrorResponse response = ErrorResponse.of(code.getHttpStatus(), code.getCode(), code.getMessage());

        return ResponseEntity.status(code.getHttpStatus()).body(response);
    }

}
