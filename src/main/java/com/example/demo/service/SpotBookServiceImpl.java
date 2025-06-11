package com.example.demo.service;

import java.sql.Timestamp;

import com.example.demo.dao.SpotBook;
import com.example.demo.dao.builder.SpotBookBuilder;
import com.example.demo.exception.SpotNotAvailableException;
import com.example.demo.exception.TimeIsExpiredException;
import com.example.demo.repository.SpotBookRepository;
import com.example.demo.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotBookServiceImpl implements SpotBookService {

    @Autowired
    SpotBookRepository spotBookRepository;

    @Autowired
    SpotRepository spotRepository;

    @Override
    public SpotBook bookSpot(Timestamp timeFrom, Timestamp timeTo, String name, String description) {
        if (timeTo.before(timeFrom) || timeTo.equals(timeFrom)) {
            throw new TimeIsExpiredException("the booking time is 0");
        }

        var bookCount = spotBookRepository.countSpotBookByTimeFromBetweenOrTimeToBetween(
            timeFrom,timeTo,
            timeFrom,timeTo
        );
        var spotCount = spotRepository.count();
        if (spotCount == 0 || spotCount <= bookCount) {
            throw new SpotNotAvailableException("there are no free parking spaces");
        } else {
            var booking = SpotBookBuilder.builder()
                .withTimeFrom(timeFrom)
                .withTimeTo(timeTo)
                .withDescription(description)
                .withBookPersonName(name)
                .build();

            return spotBookRepository.save(booking);
        }
    }

    @Override
    public void delete(Long bookId) {
        spotBookRepository.deleteById(bookId);
    }
}
