package com.example.demo.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ValidationProblemDetail extends ProblemDetail {
    private final Map<String,String> validation = new HashMap<>();

    public ValidationProblemDetail(MethodArgumentNotValidException ex) {
        ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST,"").build();
        for (var e: ex.getFieldErrors()){
            if (e != null){
                validation.put(e.getField(), e.getDefaultMessage());
            }
        }
    }

    public Map<String, String> getValidation() {
        return validation;
    }
}
