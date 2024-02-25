package com.example.demo.service;

import com.example.demo.dto.FlightDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.FlightDTOFactory;
import com.example.demo.model.Flight;
import com.example.demo.model.Trip;
import com.example.demo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightDTOFactory flightDTOFactory;

    @Autowired
    public FlightService(FlightRepository flightRepository, FlightDTOFactory flightDTOFactory) {
        this.flightRepository = flightRepository;
        this.flightDTOFactory = flightDTOFactory;
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(flightDTOFactory::createFlightDto)
                .collect(Collectors.toList());
    }

    public Flight getFlightById(Long flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        return flight.orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId));
    }
    public List<FlightDTO> searchFlights(String departureCity, String arrivalCity) {
        return flightRepository.findByDepartureAirportCityAndArrivalAirportCity(departureCity, arrivalCity)
                .stream()
                .map(flightDTOFactory::createFlightDto)
                .collect(Collectors.toList());
    }

    public Flight findFlightEntityById(Long flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId));
    }
    public FlightDTO addFlight(FlightDTO flightDTO) {
        Flight flight = flightDTOFactory.createEntity(flightDTO);
        Flight savedFlight = flightRepository.save(flight);
        return flightDTOFactory.createFlightDto(savedFlight);
    }

    public FlightDTO updateFlight(FlightDTO flightDTO) {
        if (!flightRepository.existsById(flightDTO.getFlightId())) {
            throw new IllegalArgumentException("Flight not found with ID: " + flightDTO.getFlightId());
        }
        Flight flight = flightDTOFactory.createEntity(flightDTO);
        Flight updatedFlight = flightRepository.save(flight);
        return flightDTOFactory.createFlightDto(updatedFlight);
    }

    public void deleteFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }
}
