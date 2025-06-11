package com.example.demo.exception;

public class TimeIsExpiredException extends RuntimeException {
    public TimeIsExpiredException(String s) {
        super(s);
    }
}
