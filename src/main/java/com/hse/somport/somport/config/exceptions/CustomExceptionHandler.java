package com.hse.somport.somport.config.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handler(Exception ex, WebRequest request) throws Exception {
        return handleException(ex, request);
    }

    @ExceptionHandler(SomportNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(SomportNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(getErrorBody(NOT_FOUND, request, e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(SomportConflictException.class)
    public ResponseEntity<Object> handleNotFoundException(SomportConflictException e, WebRequest request) {
        return new ResponseEntity<>(getErrorBody(CONFLICT, request, e.getMessage()), CONFLICT);
    }

    private Map<String, Object> getErrorBody(HttpStatus status, WebRequest request, String message) {
        return Map.of("message", message);
    }
}
