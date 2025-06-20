package com.example.demo.dto.response;

import com.example.demo.dao.Spot;
import com.example.demo.dao.SpotState;

public class SpotActionResponse {
    private final Long spotId;
    private final SpotState state;

    public SpotActionResponse(Spot spot) {
        this.spotId = spot.getId();
        this.state = spot.getState();
    }

    public Long getSpotId() {
        return spotId;
    }

    public SpotState getState() {
        return state;
    }
}
