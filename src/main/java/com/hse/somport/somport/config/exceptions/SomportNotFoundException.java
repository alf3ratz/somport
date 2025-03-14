package com.hse.somport.somport.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SomportNotFoundException extends RuntimeException {

    public SomportNotFoundException(String message) {
        super(message);
    }
}
