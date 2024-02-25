package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Airport;
import com.example.demo.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



import com.example.demo.dto.AirportDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.factory.AirportDTOFactory;
import com.example.demo.model.Airport;
import com.example.demo.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportDTOFactory airportDTOFactory;

    @Autowired
    public AirportService(AirportRepository airportRepository, AirportDTOFactory airportDTOFactory) {
        this.airportRepository = airportRepository;
        this.airportDTOFactory = airportDTOFactory;
    }

    public List<AirportDTO> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(airportDTOFactory::createAirportDto)
                .collect(Collectors.toList());
    }

    public AirportDTO getAirportById(Long airportId) {
        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "airportId", airportId));
        return airportDTOFactory.createAirportDto(airport);
    }

    public AirportDTO addAirport(AirportDTO airportDTO) {
        Airport airport = airportDTOFactory.createEntity(airportDTO);
        Airport savedAirport = airportRepository.save(airport);
        return airportDTOFactory.createAirportDto(savedAirport);
    }

    public AirportDTO updateAirport(AirportDTO airportDTO) {
        if (airportRepository.existsById(airportDTO.getAirportId())) {
            Airport airport = airportDTOFactory.createEntity(airportDTO);
            Airport updatedAirport = airportRepository.save(airport);
            return airportDTOFactory.createAirportDto(updatedAirport);
        }
        throw new ResourceNotFoundException("Airport", "airportId", airportDTO.getAirportId());
    }

    public void deleteAirport(Long airportId) {
        if (airportRepository.existsById(airportId)) {
            airportRepository.deleteById(airportId);
        } else {
            throw new ResourceNotFoundException("Airport", "airportId", airportId);
        }
    }
}
