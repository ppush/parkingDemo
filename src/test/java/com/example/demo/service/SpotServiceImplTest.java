package com.example.demo.service;

import com.example.demo.repository.SpotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpotServiceImplTest {

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    SpotService spotService;


    @AfterEach
    void deleteAll() {
        spotRepository.deleteAll();
    }

    @Test
    void addSpot() {
        for (int i = 0; i < 10; i++) {
            spotService.addSpot("spot_"+i);
        }
        var spots = spotService.getAllSpots();
        var availableSpots = spotService.getAvailableSpots();

        Assertions.assertNotNull(spots);
        Assertions.assertEquals(10,spots.size());
        Assertions.assertEquals(10,availableSpots.size());
    }

    @Test
    void removeSpot() {
        for (int i = 0; i < 10; i++) {
            spotService.addSpot("spot_"+i);
        }
        var spots = spotService.getAllSpots();
        var availableSpots = spotService.getAvailableSpots();

        Assertions.assertNotNull(spots);
        Assertions.assertEquals(10,spots.size());
        Assertions.assertEquals(10,availableSpots.size());

        for (var spot: spots) {
            spotService.removeSpot(spot.getId());
        }

        spots = spotService.getAllSpots();
        availableSpots = spotService.getAvailableSpots();

        Assertions.assertEquals(0,spots.size());
        Assertions.assertEquals(0,availableSpots.size());
    }
}