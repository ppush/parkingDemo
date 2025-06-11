package com.example.demo.controler;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import com.example.demo.dto.SpotDto;
import com.example.demo.dto.request.BookSpotRequest;
import com.example.demo.dto.response.BookSpotCancelResponse;
import com.example.demo.dto.response.BookSpotResponse;
import com.example.demo.dto.response.SpotActionResponse;

import com.example.demo.exception.TimeIsExpiredException;
import com.example.demo.service.SpotActionService;
import com.example.demo.service.SpotBookService;
import com.example.demo.service.SpotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spot")
public class SpotController {

    @Autowired
    private SpotService spotService;

    @Autowired
    private SpotBookService spotBookService;

    @Autowired
    private SpotActionService spotActionService;

    @GetMapping(path = "/")
    public List<SpotDto> getAllSpots() {
        var spots = spotService.getAllSpots();
        return spots.stream().map(SpotDto::new).toList();
    }

    @GetMapping(path = "/available")
    public List<SpotDto> getAvailableSpots() {
        var spots = spotService.getAvailableSpots();
        return spots.stream().map(SpotDto::new).toList();
    }

    @PostMapping(path = "/book")
    public BookSpotResponse bookSpot(@RequestBody @Valid BookSpotRequest book) {

        var calendar = getCalendar(book);
        Timestamp fromTime = new Timestamp(calendar.getTime().getTime());
        calendar.add(Calendar.HOUR,book.getIntervalHour());
        calendar.add(Calendar.MILLISECOND,-1);
        Timestamp toTime = new Timestamp(calendar.getTime().getTime());

        if (System.currentTimeMillis() > toTime.getTime()) {
            throw new TimeIsExpiredException("the booking time has already passed");
        }

        var booking = spotBookService.bookSpot(fromTime,
                                               toTime,
                                               book.getName(),
                                               book.getDescription());
        return new BookSpotResponse(booking);
    }

    private static Calendar getCalendar(BookSpotRequest book) {
        var calendar = Calendar.getInstance();
        calendar.setTime(book.getDateFrom());
        calendar.set(Calendar.HOUR, book.getFromHour());
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar;
    }

    @DeleteMapping(path = "/book/{bookId}")
    public BookSpotCancelResponse cancelBook(@PathVariable Long bookId) {
        spotBookService.delete(bookId);
        return new BookSpotCancelResponse(bookId);
    }

    @PostMapping(path = "/park/{bookId}")
    public SpotActionResponse parkSpot(@PathVariable Long bookId) {
        var spot = spotActionService.parkSpot(bookId);
        return new SpotActionResponse(spot);
    }

    @PostMapping(path = "/release/{spotId}")
    public SpotActionResponse releaseSpot(@PathVariable Long spotId) {
        var spot = spotActionService.releaseSpot(spotId);
        return new SpotActionResponse(spot);
    }
}
