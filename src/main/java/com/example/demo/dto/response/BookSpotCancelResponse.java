package com.example.demo.dto.response;

public class BookSpotCancelResponse {
    private final String bookId;

    public BookSpotCancelResponse(Long bookId) {
        this.bookId = String.valueOf(bookId);
    }

    public String getBookId() {
        return bookId;
    }
}
