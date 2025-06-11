package com.example.demo.dao;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="spot_book")
public class SpotBook {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "time_from")
    private Timestamp timeFrom;

    @Column(name = "time_to")
    private Timestamp timeTo;

    @Column(name = "book_name")
    private String bookPersonName;

    @Column(name = "book_state")
    private BookState state;

    public Timestamp getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Timestamp timeTo) {
        this.timeTo = timeTo;
    }

    public Timestamp getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Timestamp timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getBookPersonName() {
        return bookPersonName;
    }

    public void setBookPersonName(String bookPersonName) {
        this.bookPersonName = bookPersonName;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof SpotBook spotBook)) {
            return false;
        }

        return id.equals(spotBook.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
