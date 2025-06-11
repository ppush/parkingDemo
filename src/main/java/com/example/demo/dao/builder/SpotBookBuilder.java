package com.example.demo.dao.builder;

import java.sql.Timestamp;

import com.example.demo.dao.BookState;
import com.example.demo.dao.SpotBook;

public final class SpotBookBuilder {
    private String description;
    private Timestamp timeFrom;
    private Timestamp timeTo;
    private String bookPersonName;
    private BookState state;

    private SpotBookBuilder() {
    }

    public static SpotBookBuilder builder() {
        return new SpotBookBuilder();
    }

    public SpotBookBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SpotBookBuilder withTimeFrom(Timestamp timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public SpotBookBuilder withTimeTo(Timestamp timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public SpotBookBuilder withBookPersonName(String bookPersonName) {
        this.bookPersonName = bookPersonName;
        return this;
    }

    public SpotBookBuilder withState(BookState state) {
        this.state = state;
        return this;
    }

    public SpotBook build() {
        SpotBook spotBook = new SpotBook();
        spotBook.setDescription(description);
        spotBook.setTimeFrom(timeFrom);
        spotBook.setTimeTo(timeTo);
        spotBook.setBookPersonName(bookPersonName);
        spotBook.setState(state);
        return spotBook;
    }
}
