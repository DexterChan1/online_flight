package com.example.demo.service;

import com.example.demo.dto.PassengerDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.PassengerDTOFactory;
import com.example.demo.model.Passenger;
import com.example.demo.model.Trip;
import com.example.demo.repository.PassengerRepository;
import com.example.demo.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PassengerDTOFactory passengerDTOFactory;

    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll()
                .stream()
                .map(passengerDTOFactory::createPassengerDto)
                .collect(Collectors.toList());
    }

    public PassengerDTO getPassengerById(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", passengerId));
        return passengerDTOFactory.createPassengerDto(passenger);
    }

    public PassengerDTO createPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = passengerDTOFactory.createEntity(passengerDTO);

        // Setting the associated Trip for the Passenger
        Trip trip = tripRepository.findById(passengerDTO.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", passengerDTO.getTripId()));
        passenger.setTrip(trip);

        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerDTOFactory.createPassengerDto(savedPassenger);
    }

    public PassengerDTO updatePassenger(Long passengerId, PassengerDTO passengerDTO) {
        Passenger existingPassenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", passengerId));

        Passenger passengerToUpdate = passengerDTOFactory.createEntity(passengerDTO);
        passengerToUpdate.setPassengerId(passengerId); // Ensure the ID is retained

        // Setting the associated Trip for the Passenger
        Trip trip = tripRepository.findById(passengerDTO.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip", "id", passengerDTO.getTripId()));
        passengerToUpdate.setTrip(trip);

        Passenger updatedPassenger = passengerRepository.save(passengerToUpdate);
        return passengerDTOFactory.createPassengerDto(updatedPassenger);
    }

    public void deletePassenger(Long passengerId) {
        if (!passengerRepository.existsById(passengerId)) {
            throw new ResourceNotFoundException("Passenger", "id", passengerId);
        }
        passengerRepository.deleteById(passengerId);
    }
}
