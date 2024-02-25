package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.Objects;

@Entity

@Table(name = "trip_flight")
public class TripFlight {

    @EmbeddedId
    private TripFlightId id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @MapsId("tripId")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    @MapsId("flightId")
    private Flight flight;

    public TripFlight() {
    }
    @Builder
    public TripFlight(Trip trip, Flight flight) {
        this.id = new TripFlightId(trip.getTripId(), flight.getFlightId());
        this.trip = trip;
        this.flight = flight;
    }

    // Getters, setters, equals(), hashCode()...

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripFlight that = (TripFlight) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}