package com.example.demo.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String s) {
        super(s);
    }
}
