package com.hse.somport.somport.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SomportUnauthorizedException extends RuntimeException {

    public SomportUnauthorizedException(String message) {
        super(message);
    }
}
