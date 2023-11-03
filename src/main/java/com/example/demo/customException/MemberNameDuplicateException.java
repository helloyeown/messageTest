package com.example.demo.customException;

public class MemberNameDuplicateException extends RuntimeException {
    
    public MemberNameDuplicateException(String message) {
        super(message);
    }

}
