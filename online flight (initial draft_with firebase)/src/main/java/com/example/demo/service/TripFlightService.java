package com.example.demo.service;

import com.example.demo.model.TripFlight;
import com.example.demo.repository.TripFlightRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripFlightService {

    private final TripFlightRepository tripFlightRepository;

    public TripFlightService(TripFlightRepository tripFlightRepository) {
        this.tripFlightRepository = tripFlightRepository;
    }

    // Fetch all trip flights
    public List<TripFlight> getAllTripFlights() {
        return tripFlightRepository.findAll();
    }

    // Fetch trip flights by TripId
    public List<TripFlight> getTripFlightsByTripId(Long tripId) {
        return tripFlightRepository.findByTripId(tripId);
    }

    // Fetch trip flights by FlightId
    public List<TripFlight> getTripFlightsByFlightId(Long flightId) {
        return tripFlightRepository.findByFlightId(flightId);
    }

    // Create a new trip flight
    public TripFlight createTripFlight(TripFlight tripFlight) {
        return tripFlightRepository.save(tripFlight);
    }

    // Delete a trip flight by TripId and FlightId
    public void deleteTripFlightById(Long tripId, Long flightId) {
        tripFlightRepository.deleteByTripIdAndFlightId(tripId, flightId);
    }
}