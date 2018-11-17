package com.n26.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.n26.services.exceptions.NoDataForStatistics;
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

    @ExceptionHandler({NoDataForStatistics.class})
    public ResponseEntity<Object> handleNoDataForStatisticsException(Exception exception) {
        String defaultMessage = "No transactions fresh transactions detected to create statistics";

        if (exception.getMessage() != null) {
            defaultMessage = exception.getMessage();
        }

        return new ResponseEntity<>(defaultMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex.getCause() instanceof JsonParseException) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
