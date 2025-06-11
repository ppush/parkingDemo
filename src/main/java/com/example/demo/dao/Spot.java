package com.example.demo.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="spot")
public class Spot {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "state")
    private SpotState state;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SpotState getState() {
        return state;
    }

    public void setState(SpotState state) {
        this.state = state;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Spot spot)) {
            return false;
        }

        return id.equals(spot.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
