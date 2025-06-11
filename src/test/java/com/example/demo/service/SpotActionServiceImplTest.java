package com.example.demo.service;

import java.sql.Timestamp;

import com.example.demo.exception.BookNotAvailableException;
import com.example.demo.repository.SpotActionRepository;
import com.example.demo.repository.SpotBookRepository;
import com.example.demo.repository.SpotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpotActionServiceImplTest {

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    SpotBookRepository spotBookRepository;

    @Autowired
    SpotActionRepository spotActionRepository;

    @Autowired
    SpotActionService spotActionService;

    @Autowired
    SpotBookService spotBookService;

    @Autowired
    SpotService spotService;

    @AfterEach
    void deleteAll() {
        spotRepository.deleteAll();
        spotBookRepository.deleteAll();
        spotActionRepository.deleteAll();
    }

    @Test
    void parkSpot() {
        for (int i = 0; i < 5; i++) {
            spotService.addSpot("spot_"+i);
        }
        var spots = spotService.getAllSpots();
        Assertions.assertNotNull(spots);
        Assertions.assertEquals(5,spots.size());

        var book1 = spotBookService.bookSpot(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()+10*60*1000), "vahan", "");
        Assertions.assertNotNull(book1);

        spotActionService.parkSpot(book1.getId());

        spots = spotService.getAvailableSpots();
        Assertions.assertNotNull(spots);
        Assertions.assertEquals(4,spots.size());

        Assertions.assertThrows(BookNotAvailableException.class, () -> spotActionService.parkSpot(book1.getId()));

        spots = spotService.getAvailableSpots();
        Assertions.assertNotNull(spots);
        Assertions.assertEquals(4,spots.size());

    }

    @Test
    void releaseSpot() {

        for (int i = 0; i < 5; i++) {
            spotService.addSpot("spot_"+i);
        }
        var spots = spotService.getAllSpots();
        Assertions.assertNotNull(spots);
        Assertions.assertEquals(5,spots.size());

        var book1 = spotBookService.bookSpot(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()+10*60*1000), "vahan", "");
        Assertions.assertNotNull(book1);

        var spot = spotActionService.parkSpot(book1.getId());

        spots = spotService.getAvailableSpots();
        Assertions.assertNotNull(spots);
        Assertions.assertEquals(4,spots.size());

        spotActionService.releaseSpot(spot.getId());

        spots = spotService.getAvailableSpots();
        Assertions.assertNotNull(spots);
        Assertions.assertEquals(5,spots.size());
    }
}