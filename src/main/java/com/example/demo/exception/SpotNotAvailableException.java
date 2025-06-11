package com.example.demo.exception;

public class SpotNotAvailableException extends RuntimeException {
    public SpotNotAvailableException(String s) {
        super(s);
    }
}
