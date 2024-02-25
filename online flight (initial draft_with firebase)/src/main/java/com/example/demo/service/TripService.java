package com.example.demo.service;

import com.example.demo.dto.TripDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.TripDTOFactory;
import com.example.demo.model.Trip;
import com.example.demo.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final ServiceUtils serviceUtils;

    @Autowired
    public TripService(TripRepository tripRepository, ServiceUtils serviceUtils) {
        this.tripRepository = tripRepository;
        this.serviceUtils = serviceUtils;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", tripId));
    }

    public Trip findTripEntityById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", tripId));
    }

    public Trip addTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long id, Trip updatedTrip) {
        if (!tripRepository.existsById(id)) {
            throw new ResourceNotFoundException("Trip", "id", id);
        }

        Trip existingTrip = tripRepository.findById(id).orElseThrow();
        // Update fields from updatedTrip into existingTrip as needed

        return tripRepository.save(existingTrip);
    }

    public void deleteTrip(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResourceNotFoundException("Trip", "id", tripId);
        }
        tripRepository.deleteById(tripId);
    }

    public List<Trip> getTripsByUserId(Long userId) {
        return tripRepository.findByUser_UserId(userId);
    }

    public Trip findByIdAndUid(Long id, String uid) {
        Trip trip = getTripById(id);
        serviceUtils.validateSystemUserForEntity(trip.getUser().getUid(), uid);
        return trip;
    }

    public Trip updateTripByIdAndUid(Long id, String uid, Trip updatedTripDetails) {
        Trip trip = findByIdAndUid(id, uid);
        // Update the details of the trip with the details from updatedTripDetails

        return tripRepository.save(trip);
    }

    public void deleteTripByIdAndUid(Long id, String uid) {
        Trip trip = findByIdAndUid(id, uid);
        tripRepository.deleteById(trip.getTripId());
    }

    public List<Trip> getTripsByUid(String uid) {
        return tripRepository.findByUserUid(uid);
    }
}