package com.hse.somport.somport.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SomportConflictException extends RuntimeException {
    public SomportConflictException(String message) {
        super(message);
    }
}
