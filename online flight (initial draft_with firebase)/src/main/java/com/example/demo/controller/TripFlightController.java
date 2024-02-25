package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.TripFlightDTO;
import com.example.demo.factory.TripFlightDTOFactory;
import com.example.demo.model.TripFlight;
import com.example.demo.service.TripFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip-flights")
public class TripFlightController {

    private final TripFlightService tripFlightService;
    private final TripFlightDTOFactory tripFlightDTOFactory;

    @Autowired
    public TripFlightController(TripFlightService tripFlightService, TripFlightDTOFactory tripFlightDTOFactory) {
        this.tripFlightService = tripFlightService;
        this.tripFlightDTOFactory = tripFlightDTOFactory;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TripFlight>>> getAllTripFlights() {
        List<TripFlight> tripFlights = tripFlightService.getAllTripFlights();
        return ResponseEntity.ok(ApiResponseDTO.success(tripFlights));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ApiResponseDTO<List<TripFlight>>> getTripFlightsByTripId(@PathVariable Long tripId) {
        List<TripFlight> tripFlights = tripFlightService.getTripFlightsByTripId(tripId);
        return ResponseEntity.ok(ApiResponseDTO.success(tripFlights));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<ApiResponseDTO<List<TripFlight>>> getTripFlightsByFlightId(@PathVariable Long flightId) {
        List<TripFlight> tripFlights = tripFlightService.getTripFlightsByFlightId(flightId);
        return ResponseEntity.ok(ApiResponseDTO.success(tripFlights));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<TripFlight>> createTripFlight(@RequestBody TripFlightDTO tripFlightDTO) {
        TripFlight tripFlight = tripFlightDTOFactory.createTripFlight(tripFlightDTO);
        TripFlight createdTripFlight = tripFlightService.createTripFlight(tripFlight);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(createdTripFlight));
    }

    @DeleteMapping("/{tripId}/{flightId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteTripFlightById(
            @PathVariable Long tripId,
            @PathVariable Long flightId
    ) {
        tripFlightService.deleteTripFlightById(tripId, flightId);
        return ResponseEntity.ok(ApiResponseDTO.success(null));
    }
}
