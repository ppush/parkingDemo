package com.example.demo.service;

import java.sql.Timestamp;

import com.example.demo.dao.SpotBook;

public interface SpotBookService {
    SpotBook bookSpot(Timestamp timeFrom, Timestamp timeTo, String name, String description);

    void delete(Long bookId);
}
