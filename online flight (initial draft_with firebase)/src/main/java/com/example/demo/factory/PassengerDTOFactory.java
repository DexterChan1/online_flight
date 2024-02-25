package com.example.demo.factory;

import com.example.demo.dto.PassengerDTO;
import com.example.demo.model.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerDTOFactory {

    // Convert Passenger entity to PassengerDTO
    public PassengerDTO createPassengerDto(Passenger passenger) {
        return PassengerDTO.builder()
                .passengerId(passenger.getPassengerId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .dateOfBirth(passenger.getDateOfBirth())
                .nationality(passenger.getNationality())
                .passportNumber(passenger.getPassportNumber())
                .tripId(passenger.getTrip() != null ? passenger.getTrip().getTripId() : null)
                .build();
    }

    // Convert PassengerDTO to Passenger entity
    public Passenger createEntity(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(passengerDTO.getPassengerId());
        passenger.setFirstName(passengerDTO.getFirstName());
        passenger.setLastName(passengerDTO.getLastName());
        passenger.setDateOfBirth(passengerDTO.getDateOfBirth());
        passenger.setNationality(passengerDTO.getNationality());
        passenger.setPassportNumber(passengerDTO.getPassportNumber());

        // Note: The relationship with Trip is not directly set here
        // because you'd typically handle it in the service layer.
        // If you have a TripService or TripRepository, you can fetch the Trip based on tripId and set it.

        return passenger;
    }
}
