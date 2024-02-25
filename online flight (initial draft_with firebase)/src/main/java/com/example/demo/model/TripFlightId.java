package com.example.demo.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TripFlightId implements Serializable {
    private static final long serialVersionUID = -3287715633608041039L;
    private Long tripId;
    private Long flightId;

    public TripFlightId() {
    }

    public TripFlightId(Long tripId, Long flightId) {
        this.tripId = tripId;
        this.flightId = flightId;
    }

    public Long getTripId() {
        return tripId;
    }

    public Long getFlightId() {
        return flightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripFlightId that)) return false;
        return Objects.equals(tripId, that.tripId) && Objects.equals(flightId, that.flightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, flightId);
    }
}
