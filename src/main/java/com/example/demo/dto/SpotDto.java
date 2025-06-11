package com.example.demo.dto;

import com.example.demo.dao.Spot;
import com.example.demo.dao.SpotState;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class SpotDto {

    private final Long id;

    private final String description;

    private final SpotState state;

    public SpotDto(Spot spot) {
        id = spot.getId();
        description = spot.getDescription();
        state = spot.getState();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public SpotState getState() {
        return state;
    }
}
