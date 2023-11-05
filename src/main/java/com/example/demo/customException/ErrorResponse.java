package com.example.demo.customException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.*;

@Getter
@RequiredArgsConstructor
@Builder
public class ErrorResponse {
    
    private final boolean success = false;      // error 응답이므로 항상 false
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)  // errors 필드가 null일 때 JSON 응답에서 제외
    private final List<ValidationError> errors;

    // ErrorResponse 객체를 생성하는 팩토리 메소드
    public static ErrorResponse of(HttpStatus httpStatus, int code, String message) {
        return ErrorResponse.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }

    public static ErrorResponse of(HttpStatus httpStatus,int code, String message, BindingResult bindingResult){
        return ErrorResponse.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .errors(ValidationError.of(bindingResult))
                .build();
    }

    @Getter
    public static class ValidationError {
        private final String field;
        private final String value;
        private final String message;

        //  Spring의 FieldError 객체를 받아서 ValidationError 객체를 생성하는 생성자
        // FieldError 객체는 필드 검증에 실패한 정보를 담고 있음
        private ValidationError(FieldError fieldError) {
            this.field = fieldError.getField();
            this.value = fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString();
            this.message = fieldError.getDefaultMessage();
        }

        // BindingResult 객체를 받아서 ValidationError 객체의 리스트를 생성
        // BindingResult 객체는 필드 검증 결과를 담고 있음
        // 이 메소드를 통해 필드 검증에 실패한 모든 정보를 ValidationError 객체 리스트로 변환
        public static List<ValidationError> of(final BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream().map(ValidationError :: new).toList();
        }
    }

}
