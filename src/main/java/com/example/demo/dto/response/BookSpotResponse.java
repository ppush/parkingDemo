package com.example.demo.dto.response;

import com.example.demo.dao.SpotBook;

public class BookSpotResponse {

    private final String bookId;

    public BookSpotResponse(SpotBook booking) {
        bookId = String.valueOf(booking.getId()); //TODO Ideally, it would be desirable to use some kind of encrypted key here, but in the current context I don’t see the point
    }

    public String getBookId() {
        return bookId;
    }
}
