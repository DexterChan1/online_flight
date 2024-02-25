package com.example.demo.controller;

import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.PassengerDTO;
import com.example.demo.dto.PaymentInputDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.Payment;
import com.example.demo.service.PassengerService;
import com.example.demo.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PassengerDTO>>> getAllPassengers() {
        List<PassengerDTO> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(ApiResponseDTO.success(passengers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PassengerDTO>> getPassengerById(@PathVariable Long id) {
        PassengerDTO passenger = passengerService.getPassengerById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(passenger));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PassengerDTO>> createPassenger(@Valid @RequestBody PassengerDTO passengerDTO) {
        PassengerDTO createdPassenger = passengerService.createPassenger(passengerDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(createdPassenger));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PassengerDTO>> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerDTO passengerDTO) {
        PassengerDTO updatedPassenger = passengerService.updatePassenger(id, passengerDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedPassenger));
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    } */

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Passenger deleted successfully."));
    }
}
