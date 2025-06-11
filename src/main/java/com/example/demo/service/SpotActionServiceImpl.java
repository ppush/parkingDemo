package com.example.demo.service;

import java.sql.Timestamp;

import com.example.demo.dao.ActionType;
import com.example.demo.dao.BookState;
import com.example.demo.dao.Spot;
import com.example.demo.dao.SpotBook;
import com.example.demo.dao.SpotState;
import com.example.demo.dao.builder.SpotActionBuilder;
import com.example.demo.exception.BookNotAvailableException;
import com.example.demo.exception.SpotNotAvailableException;
import com.example.demo.exception.TimeIsExpiredException;
import com.example.demo.repository.SpotActionRepository;
import com.example.demo.repository.SpotBookRepository;
import com.example.demo.repository.SpotRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotActionServiceImpl implements SpotActionService {

    @Autowired
    SpotBookRepository spotBookRepository;

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    SpotActionRepository spotActionRepository;

    @Override
    @Transactional
    public Spot parkSpot(long bookId) {
        try {
            var book = spotBookRepository.getById(bookId);
            if (book.isEmpty()) {
                throw new BookNotAvailableException("Book with id %s not available".formatted(bookId));
            }
            var now = new Timestamp(System.currentTimeMillis());
            SpotBook spotBook = book.get();
            if (!BookState.USE.equals(spotBook.getState())
                && now.after(spotBook.getTimeFrom())
                && now.before(spotBook.getTimeTo())) {
                var oSpot = spotRepository.findByState(SpotState.FREE);
                if (oSpot.isEmpty()) {
                    return null;
                }
                var spot = oSpot.getFirst();
                spotActionRepository.save(SpotActionBuilder.builder()
                                              .withSpotBook(spotBook)
                                              .withTime(now)
                                              .withSpot(spot)
                                              .withActionType(ActionType.PARK)
                                              .build());
                spot.setState(SpotState.PARKED);
                spotBook.setState(BookState.USE);
                spotBookRepository.save(spotBook);
                return spotRepository.save(spot);
            }
            if (BookState.USE.equals(spotBook.getState())) {
                throw new BookNotAvailableException("Book with id %s already used".formatted(bookId));
            }
            if (now.before(spotBook.getTimeFrom())) {
                throw new TimeIsExpiredException("the booking time has not come yet");
            }
            if ( now.after(spotBook.getTimeTo())) {
                throw new TimeIsExpiredException("The booking time has already passed");
            }
        } catch (EntityNotFoundException e) {
            throw new BookNotAvailableException("Book with id %s not available".formatted(bookId));
        }
        return null;
    }

    @Override
    @Transactional
    public Spot releaseSpot(long spotId) {
        try {
            var oSpot = spotRepository.findByStateAndId(SpotState.PARKED, spotId);
            if (oSpot.isEmpty()) {
                throw new SpotNotAvailableException("Spot with id %s not available".formatted(spotId));
            }
            var spot = oSpot.getFirst();
            var now = new Timestamp(System.currentTimeMillis());
            spotActionRepository.save(SpotActionBuilder.builder()
                                          .withTime(now)
                                          .withSpot(spot)
                                          .withActionType(ActionType.PARK)
                                          .build());
            spot.setState(SpotState.FREE);
            return spotRepository.save(spot);
        } catch (EntityNotFoundException e) {
            throw new SpotNotAvailableException("Spot with id %s not available".formatted(spotId));
        }
    }
}
