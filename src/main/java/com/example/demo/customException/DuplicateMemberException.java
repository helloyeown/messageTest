package com.example.demo.customException;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateMemberException extends DuplicateKeyException {
    
    public DuplicateMemberException(String message) {
        super(message);
    }

}
