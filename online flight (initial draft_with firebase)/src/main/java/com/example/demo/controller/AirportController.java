package com.example.demo.controller;

import com.example.demo.model.Airport;
import com.example.demo.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.AirportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<AirportDTO>>> getAllAirports() {
        List<AirportDTO> responseDTOs = airportService.getAllAirports();
        return ResponseEntity.ok(ApiResponseDTO.success(responseDTOs));
    }

    @GetMapping("/{airportId}")
    public ResponseEntity<ApiResponseDTO<AirportDTO>> getAirportById(@PathVariable Long airportId) {
        AirportDTO responseDTO = airportService.getAirportById(airportId);
        return ResponseEntity.ok(ApiResponseDTO.success(responseDTO));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<AirportDTO>> addAirport(@RequestBody AirportDTO airportDTO) {
        AirportDTO responseDTO = airportService.addAirport(airportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.success(responseDTO));
    }

    @PutMapping("/{airportId}")
    public ResponseEntity<ApiResponseDTO<AirportDTO>> updateAirport(@PathVariable Long airportId, @RequestBody AirportDTO airportDTO) {
        // Ensure the airport's ID from the path matches the ID in the request body
        if (!airportId.equals(airportDTO.getAirportId())) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.fail("Mismatched airport IDs in request"));
        }
        AirportDTO responseDTO = airportService.updateAirport(airportDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(responseDTO));
    }

    @DeleteMapping("/{airportId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteAirport(@PathVariable Long airportId) {
        airportService.deleteAirport(airportId);
        return ResponseEntity.noContent().build();
    }
    /*@DeleteMapping("/{airportId}")
    public ResponseEntity<ApiResponseDTO<String>> deleteAirport(@PathVariable Long airportId) {
        airportService.deleteAirport(airportId);
        return ResponseEntity.ok(ApiResponseDTO.success("Airport deleted successfully."));
    } */
}

