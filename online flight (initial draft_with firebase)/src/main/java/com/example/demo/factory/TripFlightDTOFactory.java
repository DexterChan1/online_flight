package com.example.demo.factory;

import com.example.demo.dto.TripDTO;
import com.example.demo.dto.TripFlightDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Flight;
import com.example.demo.model.Trip;
import com.example.demo.model.TripFlight;
import com.example.demo.service.FlightService;
import com.example.demo.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TripFlightDTOFactory {

    @Autowired
    private TripService tripService;

    @Autowired
    private FlightService flightService;

    public static TripFlightDTO createTripFlightDto(Long tripId, Long flightId) {
        return TripFlightDTO.builder()
                .tripId(tripId)
                .flightId(flightId)
                .build();
    }

    public TripFlight createTripFlight(TripFlightDTO tripFlightDTO) {
        Trip trip = tripService.getTripById(tripFlightDTO.getTripId());

        Flight flight = flightService.getFlightById(tripFlightDTO.getFlightId());



        return TripFlight.builder()
                .trip(trip)
                .flight(flight)
                .build();
    }
}
