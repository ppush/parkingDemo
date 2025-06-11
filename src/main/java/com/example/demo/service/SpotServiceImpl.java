package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.Spot;
import com.example.demo.dao.SpotState;
import com.example.demo.dao.builder.SpotBuilder;
import com.example.demo.repository.SpotActionRepository;
import com.example.demo.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotServiceImpl implements SpotService {

    @Autowired
    SpotRepository spotRepository;

    @Override
    public List<Spot> getAllSpots() {
        return spotRepository.findAll();
    }

    @Override
    public List<Spot> getAvailableSpots() {
        return spotRepository.findAllByState(SpotState.FREE);
    }

    @Override
    public Spot addSpot(String description) {
        var spot = SpotBuilder.builder().withDescription(description).build();
        return spotRepository.save(spot);
    }

    @Override
    public boolean removeSpot(Long id) {
        var spot = spotRepository.findById(id);
        return spot.filter(this::removeSpot).isPresent();
    }

    @Override
    public boolean removeSpot(Spot spot) {
        if (spot.getState() == SpotState.FREE) {
            spotRepository.delete(spot);
            return true;
        }
        return false;
    }
}
