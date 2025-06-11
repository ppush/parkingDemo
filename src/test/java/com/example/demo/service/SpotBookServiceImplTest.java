package com.example.demo.service;

import java.sql.Timestamp;

import com.example.demo.exception.SpotNotAvailableException;
import com.example.demo.repository.SpotBookRepository;
import com.example.demo.repository.SpotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpotBookServiceImplTest {
    @Autowired
    SpotRepository spotRepository;

    @Autowired
    SpotBookRepository spotBookRepository;

    @Autowired
    SpotBookService spotBookService;

    @Autowired
    SpotService spotService;

    @AfterEach
    void deleteAll() {
        spotRepository.deleteAll();
        spotBookRepository.deleteAll();
    }

    @Test
    void bookSpot() {
        for (int i = 0; i < 1; i++) {
            spotService.addSpot("spot_"+i);
        }
        var spots = spotService.getAllSpots();
        Assertions.assertNotNull(spots);
        Assertions.assertEquals(1,spots.size());

        var book1 = spotBookService.bookSpot(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()+10*60*1000), "vahan", "");
        Assertions.assertThrows(SpotNotAvailableException.class,
                                () -> spotBookService.bookSpot(new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()+10*60*1000),"vahan",""));
        Assertions.assertNotNull(book1);
    }

    @Test
    void bookSpot2() {
        for (int i = 0; i < 1; i++) {
            spotService.addSpot("spot_"+i);
        }
        var spots = spotService.getAllSpots();
        Assertions.assertNotNull(spots);
        Assertions.assertEquals(1,spots.size());

        var book1 = spotBookService.bookSpot(new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()+10*60*1000),"vahan","");
        var book2 = spotBookService.bookSpot(new Timestamp(System.currentTimeMillis()+10*60*1000+1),new Timestamp(System.currentTimeMillis()+20*60*1000),"vahan","");
        Assertions.assertNotNull(book1);
        Assertions.assertNotNull(book2);
    }
}