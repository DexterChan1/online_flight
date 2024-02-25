package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.FlightDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.FlightDTOFactory;
import com.example.demo.model.Flight;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightDTOFactory flightDTOFactory;

    @Autowired
    public FlightController(FlightService flightService, FlightDTOFactory flightDTOFactory) {
        this.flightService = flightService;
        this.flightDTOFactory = flightDTOFactory;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<FlightDTO>>> getAllFlights() {
        List<FlightDTO> flightDTOs = flightService.getAllFlights();
        return ResponseEntity.ok(ApiResponseDTO.success(flightDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<FlightDTO>> getFlightById(@PathVariable Long id) throws ResourceNotFoundException {
        Flight flight = flightService.getFlightById(id);
        FlightDTO flightDTO = flightDTOFactory.createFlightDto(flight);

        ApiResponseDTO<FlightDTO> response = ApiResponseDTO.success(flightDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping(params = {"departureAirportCity", "arrivalAirportCity"})
    public ResponseEntity<ApiResponseDTO<List<FlightDTO>>> searchFlights(
            @RequestParam String departureAirportCity,
            @RequestParam String arrivalAirportCity) {

        List<FlightDTO> flights = flightService.searchFlights(departureAirportCity, arrivalAirportCity);
        ApiResponseDTO<List<FlightDTO>> response = ApiResponseDTO.success(flights);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<FlightDTO>> addFlight(@RequestBody FlightDTO flightDTO) {
        FlightDTO responseDTO = flightService.addFlight(flightDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.success(responseDTO));
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<ApiResponseDTO<FlightDTO>> updateFlight(@PathVariable Long flightId, @RequestBody FlightDTO flightDTO) {
        // Ensure the flight's ID from the path matches the ID in the request body
        if (!flightId.equals(flightDTO.getFlightId())) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.fail("Mismatched flight IDs in request"));
        }
        FlightDTO responseDTO = flightService.updateFlight(flightDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(responseDTO));
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }

    /*@DeleteMapping("/{flightId}")
    public ResponseEntity<ApiResponseDTO<String>> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.ok(ApiResponseDTO.success("Flight deleted successfully."));
    } */
}
