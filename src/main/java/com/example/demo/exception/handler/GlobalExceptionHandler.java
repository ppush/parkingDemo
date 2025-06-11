package com.example.demo.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        Exception.class
    })
    ResponseEntity<ProblemDetail> generalExceptionHandler(Exception ex, WebRequest request) {
        var body =  ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
        return new ResponseEntity(body.getBody(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class
    })
    ResponseEntity<ProblemDetail> validationExceptionHandler(MethodArgumentNotValidException ex, WebRequest request) {
        var body =  new ValidationProblemDetail(ex);
        return new ResponseEntity(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}