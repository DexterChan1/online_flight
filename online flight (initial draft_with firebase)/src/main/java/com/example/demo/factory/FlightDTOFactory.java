package com.example.demo.factory;

import com.example.demo.dto.FlightDTO;
import com.example.demo.model.Airport;
import com.example.demo.model.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightDTOFactory {

    public FlightDTO createFlightDto(Flight flight) {
        Airport departureAirport = flight.getDepartureAirport();
        Airport arrivalAirport = flight.getArrivalAirport();

        return FlightDTO.builder()
                .flightId(flight.getFlightId())
                .flightNumber(flight.getFlightNumber())
                .aircraftType(flight.getAircraftType())
                .airline(flight.getAirline())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .economyClassFare(flight.getEconomyClassFare())
                .businessClassFare(flight.getBusinessClassFare())
                .firstClassFare(flight.getFirstClassFare())
                .departureAirportId(departureAirport != null ? departureAirport.getAirportId() : null)
                .departureAirportName(departureAirport != null ? departureAirport.getAirportName() : null)
                .departureAirportCity(departureAirport != null ? departureAirport.getCity() : null)
                .departureAirportCountry(departureAirport != null ? departureAirport.getCountry() : null)
                .arrivalAirportId(arrivalAirport != null ? arrivalAirport.getAirportId() : null)
                .arrivalAirportName(arrivalAirport != null ? arrivalAirport.getAirportName() : null)
                .arrivalAirportCity(arrivalAirport != null ? arrivalAirport.getCity() : null)
                .arrivalAirportCountry(arrivalAirport != null ? arrivalAirport.getCountry() : null)
                .build();
    }

    public Flight createEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setFlightId(flightDTO.getFlightId());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setAircraftType(flightDTO.getAircraftType());
        flight.setAirline(flightDTO.getAirline());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setArrivalTime(flightDTO.getArrivalTime());
        flight.setEconomyClassFare(flightDTO.getEconomyClassFare());
        flight.setBusinessClassFare(flight.getBusinessClassFare());
        flight.setFirstClassFare(flightDTO.getFirstClassFare());

        // Only setting the IDs for departure and arrival airports, assuming the rest of the information
        // will be fetched and populated by your ORM (like Hibernate) when needed.
        Airport departureAirport = new Airport();
        departureAirport.setAirportId(flightDTO.getDepartureAirportId());
        flight.setDepartureAirport(departureAirport);

        Airport arrivalAirport = new Airport();
        arrivalAirport.setAirportId(flightDTO.getArrivalAirportId());
        flight.setArrivalAirport(arrivalAirport);
        return flight;
    }
}
