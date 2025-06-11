package com.example.demo.dao.builder;

import java.sql.Date;
import java.sql.Timestamp;

import com.example.demo.dao.ActionType;
import com.example.demo.dao.Spot;
import com.example.demo.dao.SpotAction;
import com.example.demo.dao.SpotBook;

public final class SpotActionBuilder {
    private String description;
    private Timestamp time;
    private ActionType actionType;
    private Spot spot;
    private SpotBook spotBook;

    private SpotActionBuilder() {
    }

    public static SpotActionBuilder builder() {
        return new SpotActionBuilder();
    }

    public SpotActionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SpotActionBuilder withTime(Timestamp time) {
        this.time = time;
        return this;
    }

    public SpotActionBuilder withActionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public SpotActionBuilder withSpot(Spot spot) {
        this.spot = spot;
        return this;
    }

    public SpotActionBuilder withSpotBook(SpotBook spotBook) {
        this.spotBook = spotBook;
        return this;
    }

    public SpotAction build() {
        SpotAction spotAction = new SpotAction();
        spotAction.setDescription(description);
        spotAction.setTime(time);
        spotAction.setActionType(actionType);
        spotAction.setSpot(spot);
        spotAction.setSpotBook(spotBook);
        return spotAction;
    }
}
