package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.TripDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.TripDTOFactory;
import com.example.demo.model.Flight;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.service.FlightService;
import com.example.demo.service.TripService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;
    private final TripDTOFactory tripDTOFactory;
    private final UserService userService;
    private final FlightService flightService;

    @Autowired
    public TripController(TripService tripService, TripDTOFactory tripDTOFactory, UserService userService, FlightService flightService) {
        this.tripService = tripService;
        this.tripDTOFactory = tripDTOFactory;
        this.userService = userService;
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TripDTO>>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        List<TripDTO> tripDTOs = trips.stream()
                .map(tripDTOFactory::createTripDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(tripDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TripDTO>> getTripById(@PathVariable Long id) throws ResourceNotFoundException {
        Trip trip = tripService.getTripById(id);
        TripDTO tripDTO = tripDTOFactory.createTripDto(trip);

        return ResponseEntity.ok(ApiResponseDTO.success(tripDTO));
    }
    @PostMapping
    public ResponseEntity<ApiResponseDTO<TripDTO>> createTrip(@RequestBody TripDTO tripDTO, Principal principal) {
        // Get the currently authenticated user from the database using the UID (from the principal object)
        User user = userService.getUserByUid(principal.getName());

        // Fetch departure flight using the provided ID
        Flight departureFlight = flightService.getFlightById(tripDTO.getDepartureFlightId());

        // Check if returningFlightId is provided, if not, returningFlight remains null
        Flight returningFlight = null;
        if (tripDTO.getReturningFlightId() != null) {
            returningFlight = flightService.getFlightById(tripDTO.getReturningFlightId());
        }

        // Create a Trip object from the provided TripDTO and the authenticated user
        Trip trip = Trip.builder()
                .departureFlight(departureFlight)
                .returningFlight(returningFlight)
                .departureDate(tripDTO.getDepartureDate())
                .returningDate(tripDTO.getReturningDate())
                .numberOfPassengers(tripDTO.getNumberOfPassengers())
                .tripType(tripDTO.getTripType())
                .departureClassType(tripDTO.getDepartureClassType())
                .returningClassType(tripDTO.getReturningClassType())
//                .paymentStatus(tripDTO.getPaymentStatus())
                .user(user)
                .build();
        // Save the trip using the trip service
        Trip createdTrip = tripService.addTrip(trip);
        TripDTO createdTripDTO = tripDTOFactory.createTripDto(createdTrip);

        // You no longer need to convert it back to a DTO since it already is one
        ApiResponseDTO<TripDTO> response = ApiResponseDTO.success(createdTripDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<List<TripDTO>>> getTripsByUserId(@PathVariable Long userId) throws ResourceNotFoundException {
        List<Trip> trips = tripService.getTripsByUserId(userId);
        List<TripDTO> tripDTOs = trips.stream()
                .map(tripDTOFactory::createTripDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(tripDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TripDTO>> updateTrip(@PathVariable Long id, @RequestBody TripDTO tripDTO) {
        try {
            Trip existingTrip = tripService.getTripById(id);

            // Update the existingTrip with values from the tripDTO
            existingTrip.setDepartureFlight(flightService.getFlightById(tripDTO.getDepartureFlightId()));

            if(tripDTO.getReturningFlightId() != null) {
                existingTrip.setReturningFlight(flightService.getFlightById(tripDTO.getReturningFlightId()));
            } else {
                existingTrip.setReturningFlight(null); // Explicitly set to null for one-way trip
            }

            existingTrip.setDepartureDate(tripDTO.getDepartureDate());
            existingTrip.setReturningDate(tripDTO.getReturningDate());
            existingTrip.setNumberOfPassengers(tripDTO.getNumberOfPassengers());
            existingTrip.setTripType(tripDTO.getTripType());
            existingTrip.setDepartureClassType(tripDTO.getDepartureClassType());
            existingTrip.setReturningClassType(tripDTO.getReturningClassType());
//            existingTrip.setPaymentStatus(tripDTO.getPaymentStatus());

            // If you later need to update the user as well, follow a similar conditional pattern as above.

            Trip updatedTrip = tripService.updateTrip(id, existingTrip);
            TripDTO updatedTripDTO = tripDTOFactory.createTripDto(updatedTrip);

            return ResponseEntity.ok(ApiResponseDTO.success(updatedTripDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.fail("Trip not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteTripById(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
