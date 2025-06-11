package com.example.demo.service;

import com.example.demo.dao.Spot;

public interface SpotActionService {
    Spot parkSpot(long bookId);
    Spot releaseSpot(long spotId);
}
