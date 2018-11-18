package com.n26.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.services.exceptions.InvalidTransactionException;
import com.n26.services.exceptions.TooOldTransactionException;
import com.n26.services.exceptions.TransactionHasFutureDateException;
import com.n26.services.exceptions.TransactionsListFullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

/**
 * Controller advice to create custom exception handling
 */
@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TooOldTransactionException.class})
    public ResponseEntity<Object> handleTooOldTransactionException(Exception ex) {
        log.debug(Arrays.toString(ex.getStackTrace()));
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({InvalidTransactionException.class})
    public ResponseEntity<Object> handleInvalidTransactionException(Exception ex) {
        log.debug(Arrays.toString(ex.getStackTrace()));
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TransactionHasFutureDateException.class})
    public ResponseEntity<Object> handleTransactionHasFutureDateException(Exception ex) {
        log.debug(Arrays.toString(ex.getStackTrace()));
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({TransactionsListFullException.class})
    public ResponseEntity<Object> handleTransactionsListFullException(Exception ex) {
        log.debug(Arrays.toString(ex.getStackTrace()));
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            log.debug(Arrays.toString(ex.getStackTrace()));
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.debug(Arrays.toString(ex.getStackTrace()));
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
