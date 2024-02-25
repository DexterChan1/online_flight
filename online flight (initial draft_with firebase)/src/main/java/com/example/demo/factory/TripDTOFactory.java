package com.example.demo.factory;

import com.example.demo.dto.TripDTO;
import com.example.demo.model.Flight;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.service.FlightService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TripDTOFactory {

    @Autowired
    private UserService userService;

    @Autowired
    private FlightService flightService;

    public TripDTO createTripDto(Trip trip) {
        // Extract details for departure and returning flights
        Flight departureFlight = flightService.getFlightById(trip.getDepartureFlight().getFlightId());
        Flight returningFlight = null;
        if (trip.getReturningFlight() != null) {
            returningFlight = flightService.getFlightById(trip.getReturningFlight().getFlightId());
        }

        return TripDTO.builder()
                .tripId(trip.getTripId())
                .userId(trip.getUser().getUserId())
                .userName(trip.getUser().getUserName())
                .departureFlightId(departureFlight.getFlightId())
                .departureFlightNumber(departureFlight.getFlightNumber())
                .departureAirportName(departureFlight.getDepartureAirport().getAirportName())
                .departureAirportCity(departureFlight.getDepartureAirport().getCity())
                .departureAirportCountry(departureFlight.getDepartureAirport().getCountry())
                .departureArrivalAirportName(departureFlight.getArrivalAirport().getAirportName())
                .departureArrivalAirportCity(departureFlight.getArrivalAirport().getCity())
                .departureArrivalAirportCountry(departureFlight.getArrivalAirport().getCountry())
                .departureTime(departureFlight.getDepartureTime()) // Departure time of the flight
                .arrivalTime(departureFlight.getArrivalTime())   // Arrival time of the flight
                // Uncomment and set the fare fields if needed
                .departureEconomyClassFare(departureFlight.getEconomyClassFare())
                .departureBusinessClassFare(departureFlight.getBusinessClassFare())
                .departureFirstClassFare(departureFlight.getFirstClassFare())
                .returningFlightId(returningFlight != null ? returningFlight.getFlightId() : null)
                .returningFlightNumber(returningFlight != null ? returningFlight.getFlightNumber() : null)
                .returningAirportName(returningFlight != null ? returningFlight.getDepartureAirport().getAirportName() : null)
                .returningAirportCity(returningFlight != null ? returningFlight.getDepartureAirport().getCity() : null)
                .returningAirportCountry(returningFlight != null ? returningFlight.getDepartureAirport().getCountry() : null)
                .returningArrivalAirportName(returningFlight != null ? returningFlight.getArrivalAirport().getAirportName() : null)
                .returningArrivalAirportCity(returningFlight != null ? returningFlight.getArrivalAirport().getCity() : null)
                .returningArrivalAirportCountry(returningFlight != null ? returningFlight.getArrivalAirport().getCountry() : null)
                .returningDepartureTime(returningFlight != null ? returningFlight.getDepartureTime() : null) // Departure time for the return flight
                .returningArrivalTime(returningFlight != null ? returningFlight.getArrivalTime() : null)   // Arrival time for the return flight
                // Uncomment and set the fare fields if needed
                .returningEconomyClassFare(returningFlight != null ? returningFlight.getEconomyClassFare() : null)
                .returningBusinessClassFare(returningFlight != null ? returningFlight.getBusinessClassFare() : null)
                .returningFirstClassFare(returningFlight != null ? returningFlight.getFirstClassFare() : null)

                .departureDate(trip.getDepartureDate())
                .returningDate(trip.getReturningDate())
                .numberOfPassengers(trip.getNumberOfPassengers())
                .tripType(trip.getTripType())
                .departureClassType(trip.getDepartureClassType())
                .returningClassType(returningFlight != null ? trip.getReturningClassType() : null)
//                .paymentStatus(trip.getPaymentStatus())
                .build();
    }

    public Trip createEntity(TripDTO tripDTO) {
        Trip trip = new Trip();
        trip.setTripId(tripDTO.getTripId());

        // Fetch User based on userId and set it to the Trip entity
        User user = userService.getUserById(tripDTO.getUserId());
        trip.setUser(user);

        // Fetch departure and returning Flights based on their IDs and set them to the Trip entity
        Flight departureFlight = flightService.findFlightEntityById(tripDTO.getDepartureFlightId());
        trip.setDepartureFlight(departureFlight);

        if(tripDTO.getReturningFlightId() != null) {
            Flight returningFlight = flightService.findFlightEntityById(tripDTO.getReturningFlightId());
            trip.setReturningFlight(returningFlight);
        }

        // Handle the payment
        // Note: Typically, the payment entity is not directly created from the DTO due to security concerns.
        // Instead, payment handling would be done through a specific service method that processes payments.

        trip.setDepartureDate(tripDTO.getDepartureDate());
        trip.setReturningDate(tripDTO.getReturningDate());
        trip.setNumberOfPassengers(tripDTO.getNumberOfPassengers());
        trip.setTripType(tripDTO.getTripType());
        trip.setDepartureClassType(tripDTO.getDepartureClassType());
        trip.setReturningClassType(tripDTO.getReturningClassType());
//        trip.setPaymentStatus(tripDTO.getPaymentStatus());
        return trip;
    }

}
