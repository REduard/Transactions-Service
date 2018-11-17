package com.n26.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.services.exceptions.TooOldTransactionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TooOldTransactionException.class})
    public ResponseEntity<Object> handleTooOldTransactionException() {

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException || cause == null) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
