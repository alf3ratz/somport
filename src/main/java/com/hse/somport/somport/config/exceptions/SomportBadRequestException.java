package com.hse.somport.somport.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SomportBadRequestException extends RuntimeException {
    public SomportBadRequestException(String message) {
        super(message);
    }

}
