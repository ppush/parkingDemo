package com.example.demo.dao;

import java.sql.Timestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="spot_action")
public class SpotAction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "action_type")
    private ActionType actionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "spot_id", referencedColumnName = "id")
    private Spot spot;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "spot_book_id", referencedColumnName = "id")
    private SpotBook spotBook;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public SpotBook getSpotBook() {
        return spotBook;
    }

    public void setSpotBook(SpotBook spotBook) {
        this.spotBook = spotBook;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof SpotAction that)) {
            return false;
        }

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
