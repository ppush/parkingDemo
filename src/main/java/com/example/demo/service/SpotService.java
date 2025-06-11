package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.Spot;

public interface SpotService {
    List<Spot> getAllSpots();
    List<Spot> getAvailableSpots();
    Spot addSpot(String description);
    boolean removeSpot(Spot spot);
    boolean removeSpot(Long id);
}
