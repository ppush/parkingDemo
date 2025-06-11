package com.example.demo.dto.response;

import com.example.demo.dao.Spot;

public class AddSpotResponse {
    private final String description;
    private final Long spotId;

    public AddSpotResponse(Spot spot) {
        spotId = spot.getId();
        description = spot.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public Long getSpotId() {
        return spotId;
    }

}
