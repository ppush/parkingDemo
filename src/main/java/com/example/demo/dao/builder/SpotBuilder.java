package com.example.demo.dao.builder;

import java.util.Objects;

import com.example.demo.dao.Spot;
import com.example.demo.dao.SpotState;

public final class SpotBuilder {
    private String description;
    private SpotState state;

    private SpotBuilder() {
    }

    public static SpotBuilder builder() {
        return new SpotBuilder();
    }

    public SpotBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SpotBuilder withState(SpotState state) {
        this.state = state;
        return this;
    }

    public Spot build() {
        Spot spot = new Spot();
        spot.setDescription(description);
        spot.setState(Objects.requireNonNullElse(state, SpotState.FREE));
        return spot;
    }
}
